package com.exampe;

import cc.mallet.classify.Classifier;
import cc.mallet.classify.ClassifierTrainer;
import cc.mallet.classify.MaxEntTrainer;
import cc.mallet.classify.NaiveBayesTrainer;
import cc.mallet.pipe.Pipe;
import cc.mallet.pipe.iterator.CsvIterator;
import cc.mallet.types.Instance;
import cc.mallet.types.InstanceList;
import cc.mallet.types.Labeling;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by user50 on 18.10.2014.
 */
public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ClassifierBuilder builder = new ClassifierBuilder();
//        builder.build();

        Classifier classifier = builder.loadClassifier();

        long start = System.currentTimeMillis();
        classify(classifier, "importante");
        System.out.println(System.currentTimeMillis() - start);
    }

    public static void classify(Classifier classifier, String text)
    {
        ArrayList<Instance> instances = new ArrayList<>();
        instances.add(new Instance(new StringBuffer(text), null, "unknown", text));

        Iterator iterator = classifier.getInstancePipe().newIteratorFrom(instances.iterator());

        Labeling labeling = classifier.classify(iterator.next()).getLabeling();

        // print the labels with their weights in descending order (ie best first)

        for (int rank = 0; rank < labeling.numLocations(); rank++){
            System.out.print(labeling.getLabelAtRank(rank) + ":" +
                    labeling.getValueAtRank(rank) + " ");
        }
        System.out.println();

    }

    public Classifier trainClassifier(InstanceList trainingInstances) {

        // Here we use a maximum entropy (ie polytomous logistic regression)
        //  classifier. Mallet includes a wide variety of classification
        //  algorithms, see the JavaDoc API for details.

        ClassifierTrainer trainer = new NaiveBayesTrainer();
        return trainer.train(trainingInstances);
    }



}
