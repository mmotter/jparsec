package org.jparsec.examples.set;

import static org.jparsec.Scanners.isChar;

import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

import org.jparsec.OperatorTable;
import org.jparsec.Parser;
import org.jparsec.Parsers;
import org.jparsec.Scanners;
import org.jparsec.Terminals;

public final class SetParser {

    static final Parser<Boolean> TRUE = Scanners.TRUE.map(Boolean::valueOf);

    static final Parser<Boolean> FALSE = Scanners.FALSE.map(Boolean::valueOf);

   // static final Parser<Boolean> BOOLEAN = Terminals.StringLiteral.PARSER..map(Boolean::valueOf);

    static final Parser<Void> IGNORED = Parsers.or(
            Scanners.WHITESPACES).skipMany();

    private static final Terminals OPERATORS =
            Terminals.operators("!", "|", "&", "(", ")");

    static final Parser<?> TOKENIZER =
            Parsers.or(Terminals.DecimalLiteral.TOKENIZER, OPERATORS.tokenizer());

    /** Parsers {@code source} and evaluates to an {@link Integer}. */
    public static Boolean evaluate(String source) {
        return parser().parse(source);
    }



    static final BinaryOperator<Boolean> UNION = (a, b) -> a | b;

    static final BinaryOperator<Boolean> INTERSECTION = (a, b) -> a & b;

    static final UnaryOperator<Boolean> COMPLEMENT = a -> !a;



    private static <T> Parser<T> op(char ch, T value) {
        return isChar(ch).retn(value);
    }

    static Parser<Boolean> parser() {
        Parser.Reference<Boolean> ref = Parser.newReference();
        Parser<Boolean> term = ref.lazy().between(isChar('('), isChar(')')).or(TRUE).or(FALSE);
        Parser<Boolean> parser = new OperatorTable<Boolean>()
                .prefix(op('!', COMPLEMENT), 100)
                .infixl(op('|', UNION), 10)
                .infixl(op('&', INTERSECTION), 10)
                .build(term);
        ref.set(parser);
        return parser;
    }
}
