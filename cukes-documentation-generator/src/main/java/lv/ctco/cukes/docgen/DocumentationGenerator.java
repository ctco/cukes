package lv.ctco.cukes.docgen;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.reflect.ClassPath;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class DocumentationGenerator {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Map<CukesComponent, Multimap<StepType, StepDefinition>> steps = collectSteps();

        String header = IOUtils.toString(DocumentationGenerator.class.getResourceAsStream("/header.md"));
        new File("target").mkdirs();
        Writer writer = new FileWriter("target/steps.md");
        writer.write(header + "\n");
        generateMarkdown(steps, writer);
        writer.close();
    }

    private static void generateMarkdown(Map<CukesComponent, Multimap<StepType, StepDefinition>> steps, Writer w) throws IOException {
        PrintWriter writer = new PrintWriter(w);
        for (Map.Entry<CukesComponent, Multimap<StepType, StepDefinition>> entry : steps.entrySet()) {
            CukesComponent component = entry.getKey();
            Multimap<StepType, StepDefinition> stepsByType = entry.getValue();
            writer.println("## " + component.getName() + " steps");
            writer.println();

            generateRequiredDependencies(writer, component);

            for (Map.Entry<StepType, Collection<StepDefinition>> definitionEntry : stepsByType.asMap().entrySet()) {
                StepType stepType = definitionEntry.getKey();
                Collection<StepDefinition> stepDefinitions = definitionEntry.getValue();
                if (stepDefinitions.isEmpty()) {
                    continue;
                }
                writer.println("### " + stepType);
                writer.println();
                writer.println("|Pattern|Description|");
                writer.println("|-------|-----------|");
                for (StepDefinition stepDefinition : stepDefinitions) {
                    String patten = stepDefinition.getPatten();
                    patten = patten.replaceAll("\\|", "\\\\|").
                        replaceAll("_", "\\_").
                        replaceAll("\\*", "\\\\*").
                        replace("^", "").
                        replace("$", "");
                    String description = stepDefinition.getDescription() == null ? "" : stepDefinition.getDescription();
                    writer.println("|" + patten + "|" + description + "|");
                }
                writer.println();
            }
        }
    }

    private static void generateRequiredDependencies(PrintWriter writer, CukesComponent component) {
        writer.println("Required dependencies:");
        writer.println();
        writer.println("**Maven**");
        writer.println();
        writer.println("```xml");
        writer.println("<dependency>");
        writer.println("    <groupId>lv.ctco.cukes</groupId>");
        writer.println("    <artifactId>" + component.getModuleName() + "</artifactId>");
        writer.println("    <version>${cukes.version}</version>");
        writer.println("</dependency>");
        writer.println("```");
        writer.println();
        writer.println("**Gradle**");
        writer.println();
        writer.println("```");
        writer.println("testCompile(\"lv.ctco.cukes:" + component.getModuleName() + ":${cukes.version}\");");
        writer.println("```");
        writer.println();
    }

    private static Map<CukesComponent, Multimap<StepType, StepDefinition>> collectSteps() throws IOException, ClassNotFoundException {
        Map<CukesComponent, Multimap<StepType, StepDefinition>> steps = createStepsStubs();
        ClassPath classPath = ClassPath.from(DocumentationGenerator.class.getClassLoader());
        ImmutableSet<ClassPath.ClassInfo> classes = classPath.getTopLevelClassesRecursive("lv.ctco.cukes");
        for (ClassPath.ClassInfo classInfo : classes) {
            String className = classInfo.getName();
            Class<?> aClass = Class.forName(className);
            Method[] methods = aClass.getMethods();
            for (Method method : methods) {
                StepType type = StepType.getTypeForMethod(method);
                if (type != null) {
                    CukesComponent component = CukesComponent.findByClassName(className);
                    steps.get(component).put(type, new StepDefinition(type.getPattern(method), type.getDescription(method)));
                }
             }
        }
        return steps;
    }

    private static Map<CukesComponent, Multimap<StepType, StepDefinition>> createStepsStubs() {
        Map<CukesComponent, Multimap<StepType, StepDefinition>> steps = new LinkedHashMap<>();
        for (CukesComponent component : CukesComponent.values()) {
            LinkedHashMultimap<StepType, StepDefinition> stepsMap = LinkedHashMultimap.create();
            steps.put(component, stepsMap);
        }
        return steps;
    }
}
