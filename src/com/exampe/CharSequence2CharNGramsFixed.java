package com.exampe;

import cc.mallet.pipe.CharSequence2CharNGrams;

/**
 * Created by user50 on 18.10.2014.
 */
public class CharSequence2CharNGramsFixed extends CharSequence2CharNGrams {
    private int n;

    public CharSequence2CharNGramsFixed(int n, boolean distinguishBorders) {
        super(n, distinguishBorders);
        this.n = n;
    }

    @Override
    protected String[] ngramify(CharSequence s) {
        if (s.length() < n)
            return super.ngramify(s+"11111");

        return super.ngramify(s);
    }
}
