package com.exampe;

import cc.mallet.classify.Classifier;
import cc.mallet.classify.ClassifierTrainer;
import cc.mallet.classify.NaiveBayesTrainer;
import cc.mallet.pipe.Pipe;
import cc.mallet.pipe.iterator.CsvIterator;
import cc.mallet.types.Instance;

import java.io.*;
import java.util.Iterator;

/**
 * Created by user50 on 18.10.2014.
 */
public class ClassifierBuilder {


    private static final String SOURCE = "data/data.txt";

    private String classifierFileName = "data/classifier";

    public void build() throws IOException {
        Pipe pipe = new ImportExample().pipe;
        Iterator<Instance> instanceIterator = getInstanceIterator(pipe, new File(SOURCE));
        BufferedInstanceIterator iterator = new BufferedInstanceIterator(instanceIterator, pipe, 1000 );

        int count = 0;
        Classifier classifier = null;
        while (iterator.hasNext()) {
            ClassifierTrainer trainer = classifier == null ? new NaiveBayesTrainer() : new NaiveBayesTrainer((cc.mallet.classify.NaiveBayes) classifier);
            classifier = trainer.train(iterator.next());
            System.out.println(++count);
        }

        saveClassifier(classifier, new File(classifierFileName));

    }

    public Iterator<Instance> getInstanceIterator(Pipe pipe, File file) throws IOException {

        // Create a new iterator that will read raw instance data from
        //  the lines of a file.
        // Lines should be formatted as:
        //
        //   [name] [label] [data ... ]
        //
        //  in this case, "label" is ignored.

        CsvIterator reader =
                new CsvIterator(new FileReader(file),
                        "(\\w+)\\s+(\\w+)\\s+(.*)",
                        3, 2, 1);  // (data, label, name) field indices

        // Create an iterator that will pass each instance through
        //  the same pipe that was used to create the training data
        //  for the classifier.
        return pipe.newIteratorFrom(reader);
    }

    public Classifier loadClassifier()
            throws FileNotFoundException, IOException, ClassNotFoundException {

        // The standard way to save classifiers and Mallet data
        //  for repeated use is through Java serialization.
        // Here we load a serialized classifier from a file.

        Classifier classifier;

        ObjectInputStream ois =
                new ObjectInputStream (new FileInputStream(new File(classifierFileName)));
        classifier = (Classifier) ois.readObject();
        ois.close();

        return classifier;
    }

    public void saveClassifier(Classifier classifier, File serializedFile)
            throws IOException {

        // The standard method for saving classifiers in
        //  Mallet is through Java serialization. Here we
        //  write the classifier object to the specified file.

        ObjectOutputStream oos =
                new ObjectOutputStream(new FileOutputStream (serializedFile));
        oos.writeObject (classifier);
        oos.close();
    }

}
