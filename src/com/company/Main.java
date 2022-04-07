package com.company;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class Main {

    public static void main(String[] args) {
        // write your code here
        Patient patient = new Patient();
        List<Patient> patientList = Patient.getAllPatients();

        Set<Patient> uniquePatients = new TreeSet<>();  // unieque patients is hold in this set // opdracht 1

        uniquePatients.addAll(patientList);
        uniquePatients.stream().
                sorted(Comparator.comparing(Patient::getNationalRegistryNumber))
                .forEach(System.out::println);

        System.out.println("-------------------------------------------");


        Set<Patient> patientSet = new TreeSet<>(new TemperatureSorter().thenComparing(new PatientAgeSorter())); // opdracht 2
        patientSet.addAll(patientList);
        patientSet.forEach(System.out::println);

        System.out.println("-------------------------------------------");


        Queue <Patient> patientQueue =new ArrayDeque<>();

        patientQueue.addAll(uniquePatients);
        System.out.println(patientQueue.size());


        Map<Integer, List<Patient>> mapCollection1 = new HashMap<>();
        mapCollection1 = iterateQueue(patientQueue);

        for (Map.Entry<Integer, List<Patient>> category : mapCollection1.entrySet()) {

            int key = category.getKey();

            System.out.println(category);
        }
        System.out.println(patientQueue.size());
    }

    public static Map<Integer, List<Patient>> iterateQueue(Queue<Patient> patients){
        Map<Integer, List<Patient>> mapCollection = new HashMap<>();

        List<Patient> category1 = new ArrayList<>();
        List<Patient> category2 = new ArrayList<>();
        List<Patient> category3 = new ArrayList<>();
        List<Patient> category4 = new ArrayList<>();

        for (Patient item: patients) {
            if(item.getAge()>=65 && item.getTemperature()>=40) {

                category1.add(item);
                patients.poll();
            }

        }
       for (Patient item: patients) {
            if(item.getAge()<65 && item.getTemperature()>=38
                    || item.getTemperature()>40 && item.isUnKnownVirus()){

                category2.add(item);
                patients.poll();

            }
        }
        for (Patient item: patients) {
            if(item.isUnKnownVirus() && item.getTemperature()<=38) {

                category3.add(item);
                patients.poll();
            }

        }

        for (Patient item: patients) {
            if(item.getTemperature()>=38 && !item.isEnsured()) {
                category4.add(item);

            }
            patients.poll();
        }

        mapCollection.put(1,category1);
        mapCollection.put(2,category2);
        mapCollection.put(3,category3);
        mapCollection.put(4,category4);

        return mapCollection;

    }

}