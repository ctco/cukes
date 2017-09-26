package lv.ctco.cukes.core.facade;

import com.google.inject.Singleton;
import lv.ctco.cukes.core.CukesRuntimeException;
import lv.ctco.cukes.core.internal.context.InflateContext;
import org.apache.commons.lang3.StringUtils;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.Supplier;
import java.util.stream.IntStream;

@Singleton
@InflateContext
public class RandomGeneratorFacadeImpl implements RandomGeneratorFacade {

    private Map<Character, Supplier<Integer>> randomGenerators = new HashMap<>();

    private SecureRandom random = new SecureRandom();

    private static final int CAPITAL_CHAR_BOUNDS = 'Z' - 'A';
    private static final int CHAR_BOUNDS = 'z' - 'a';
    private static final int NUMBER_BOUNDS = '9' - '0';

    public RandomGeneratorFacadeImpl() {
        randomGenerators.put('A', () -> 'A' + random.nextInt(CAPITAL_CHAR_BOUNDS));
        randomGenerators.put('a', () -> 'a' + random.nextInt(CHAR_BOUNDS));
        randomGenerators.put('0', () -> '0' + random.nextInt(NUMBER_BOUNDS));
    }


    @Override
    public String byPattern(String pattern) {
        if (StringUtils.isEmpty(pattern))
            throw new CukesRuntimeException("Invalid password pattern, should contain  symbols like a,A,0");
        return pattern
            .chars()
            .map(this::mapPatternCharacter)
            .collect(
                producer(),
                accumulator(),
                resultMerger()
            )
            .toString();
    }

    @Override
    public String withLength(int length) {
        Character[] patterns = randomGenerators.keySet().toArray(new Character[0]);
        return IntStream
            .range(0, length)
            .map(i -> randomGenerators.get(patterns[i % patterns.length]).get())
            .collect(producer(),
                accumulator(),
                resultMerger()
            ).toString();
    }

    private BiConsumer<StringBuilder, StringBuilder> resultMerger() {
        return (cont1, cont2) -> new StringBuilder(cont1.toString()).append(cont2.toString());
    }

    private ObjIntConsumer<StringBuilder> accumulator() {
        return (container, ch) -> container.append((char) ch);
    }

    private Supplier<StringBuilder> producer() {
        return StringBuilder::new;
    }

    private int mapPatternCharacter(int ch) {
        Supplier<Integer> characterSupplier = randomGenerators.get((char) ch);
        if (characterSupplier == null)
            throw new CukesRuntimeException("Invalid password pattern character: " + ch + ". Pattern should contain combination of A,a,0");
        return characterSupplier.get();
    }
}
