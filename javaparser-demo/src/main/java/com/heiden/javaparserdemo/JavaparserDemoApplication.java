package com.heiden.javaparserdemo;

import com.github.difflib.UnifiedDiffUtils;
import com.github.difflib.patch.Patch;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.heiden.javaparserdemo.config.ScheduledConfig;
import com.heiden.javaparserdemo.util.TaskCache;
import io.reflectoring.diffparser.api.DiffParser;
import io.reflectoring.diffparser.api.UnifiedDiffParser;
import io.reflectoring.diffparser.api.model.Diff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

@SpringBootApplication
@EnableScheduling
public class JavaparserDemoApplication {

    @Autowired
    private ScheduledConfig scheduledConfig;

    public static void main(String[] args) throws IOException {
        CompilationUnit compilationUnit = StaticJavaParser.parse("class A{private int b;}");
        NodeList<TypeDeclaration<?>> ts = compilationUnit.getTypes();
        List<String> diff = Files.readAllLines(new File("/Users/heiden/Downloads/pts.diff").toPath());

        Patch<String> patch = UnifiedDiffUtils.parseUnifiedDiff(diff);

        DiffParser parser = new UnifiedDiffParser();
        InputStream in = new FileInputStream("/Users/heiden/Downloads/pts.diff");
        List<Diff> diff2 = parser.parse(in);

        TaskCache.jobName = "testJob";
        TaskCache.jobGroupName = "testGroup";

        SpringApplication.run(JavaparserDemoApplication.class, args);
    }

}
