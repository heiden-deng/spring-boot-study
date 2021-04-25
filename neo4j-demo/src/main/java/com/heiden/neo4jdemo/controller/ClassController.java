package com.heiden.neo4jdemo.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.heiden.neo4jdemo.entity.CallRelation;
import com.heiden.neo4jdemo.entity.ClassNode;
import com.heiden.neo4jdemo.entity.ContainsRelation;
import com.heiden.neo4jdemo.entity.MethodNode;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.async.ResultCursor;
import org.neo4j.driver.types.Node;
import org.neo4j.driver.types.Path;
import org.neo4j.driver.types.Relationship;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @Author: heiden
 * @Date: 2021/4/16 11:24
 * @Project: spring-boot-study
 */
@RestController
public class ClassController {

    private final Driver driver;

    public ClassController(Driver driver) {
        this.driver = driver;
    }

    @GetMapping(path = "/class", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getMovieTitles() {
        List<String> ids = new ArrayList<>();
        try (Session session = driver.session()) {
            Result result = session.run("match p=(m1:Method)<-[*..100]-() where id(m1)=19 return p");
            while (result.hasNext()) {
                Record record = result.next();
                Path path = record.get("p").asPath();
                Iterator<Node> nodeIterator = path.nodes().iterator();
                while (nodeIterator.hasNext()) {
                    Node node = nodeIterator.next();
                    int i = 1;
                }
            }
            return ids;
        }
    }

    private MethodNode convert2MethodNode(Node node) {
        MethodNode methodNode = new MethodNode();
        methodNode.setId(node.id());
        methodNode.setName(node.get("name").asString());
        methodNode.setTaskId(node.get("taskId").asString());
        return methodNode;
    }

    private ClassNode convert2ClassNode(Node node) {
        ClassNode classNode = new ClassNode();
        classNode.setId(node.id());
        classNode.setName(node.get("name").asString());
        classNode.setTaskId(node.get("taskId").asString());
        return classNode;
    }

    @GetMapping(path = "/call", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getMethodCallss() {
        Map<Long, MethodNode> methodNodeMap = new HashMap<>();
        Map<Long, CallRelation> callRelationMap = new HashMap<>();
        JSONObject callChainJson = new JSONObject();
        JSONArray nodeAry = new JSONArray();
        JSONArray relationAry = new JSONArray();
        try (Session session = driver.session()) {
            Result result = session.run("match p=(:Method{name:\"methodA0\"})-[*..5]->() return p");

            while (result.hasNext()) {
                Record record = result.next();
                Path path = record.get("p").asPath();
                Iterator<Node> nodeIterator = path.nodes().iterator();
                while (nodeIterator.hasNext()) {
                    Node node = nodeIterator.next();
                    MethodNode methodNode = convert2MethodNode(node);
                    methodNodeMap.put(node.id(), methodNode);
                }

                Iterator<Relationship> relationshipIterator = path.relationships().iterator();
                while (relationshipIterator.hasNext()) {
                    Relationship relationship = relationshipIterator.next();
                    CallRelation callRelation = new CallRelation();
                    callRelation.setId(relationship.id());
                    callRelation.setStartId(String.valueOf(relationship.startNodeId()));
                    callRelation.setEndId(String.valueOf(relationship.endNodeId()));
                    if (relationship.hasType("Call")) {
                        callRelation.setType(relationship.type());
                    }
                    callRelationMap.put(relationship.id(), callRelation);
                }
            }
            for (MethodNode methodNode : methodNodeMap.values()) {
                nodeAry.add(methodNode.toJSON());
                Result containResult = session.run("match (cl:Class)-[ct:Contains]->(md:Method) where id(md)=" + methodNode.getId() + " return cl,ct");
                if (containResult.hasNext()) {
                    Record containRecord = containResult.next();
                    if (containRecord.containsKey("cl")) {
                        Node containNode = containRecord.get("cl").asNode();
                        ClassNode classNode = convert2ClassNode(containNode);
                        nodeAry.add(classNode.toJSON());
                    }
                    if (containRecord.containsKey("ct")) {
                        Relationship dbContainRelation = containRecord.get("ct").asRelationship();
                        ContainsRelation containsRelation = new ContainsRelation();
                        containsRelation.setStartId(String.valueOf(dbContainRelation.startNodeId()));
                        containsRelation.setEndId(String.valueOf(dbContainRelation.endNodeId()));
                        if (dbContainRelation.hasType("Contains")) {
                            containsRelation.setType(dbContainRelation.type());
                        }
                        relationAry.add(containsRelation);
                    }

                }
            }

            for (CallRelation callRelation : callRelationMap.values()) {
                relationAry.add(callRelation.toJSON());
            }


            callChainJson.put("node", nodeAry);
            callChainJson.put("relation", relationAry);

            return callChainJson.toJSONString();
        }
    }
}