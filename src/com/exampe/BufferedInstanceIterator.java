package com.exampe;

import cc.mallet.pipe.Pipe;
import cc.mallet.types.Instance;
import cc.mallet.types.InstanceList;

import java.util.Iterator;

/**
 * Created by user50 on 18.10.2014.
 */
public class BufferedInstanceIterator implements Iterator<InstanceList> {

    Iterator<Instance> instanceIterator;
    Pipe pipe;
    int bufferSize;

    public BufferedInstanceIterator(Iterator<Instance> instanceIterator, Pipe pipe, int bufferSize) {
        this.instanceIterator = instanceIterator;
        this.pipe = pipe;
        this.bufferSize = bufferSize;
    }

    @Override
    public boolean hasNext() {
        return instanceIterator.hasNext();
    }

    @Override
    public InstanceList next() {
        InstanceList list = new InstanceList(pipe, bufferSize);

        int count = 0;
        while (count < bufferSize && instanceIterator.hasNext())
            list.add(instanceIterator.next());

        return list;
    }

    @Override
    public void remove() {

    }
}
