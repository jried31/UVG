package edu.dartmouth.cs.myruns5;

class WekaAccelerometerClassifier {

	  public static double classify(Object[] i)
	    throws Exception {

	    double p = Double.NaN;
	    p = WekaAccelerometerClassifier.N36673c9f0(i);
	    return p;
	  }
	  static double N36673c9f0(Object []i) {
	    double p = Double.NaN;
	    if (i[64] == null) {
	      p = 1;
	    } else if (((Double) i[64]).doubleValue() <= 10.353474) {
	    p = WekaAccelerometerClassifier.N33bd06a01(i);
	    } else if (((Double) i[64]).doubleValue() > 10.353474) {
	      p = 2;
	    } 
	    return p;
	  }
	  static double N33bd06a01(Object []i) {
	    double p = Double.NaN;
	    if (i[0] == null) {
	      p = 0;
	    } else if (((Double) i[0]).doubleValue() <= 38.193106) {
	      p = 0;
	    } else if (((Double) i[0]).doubleValue() > 38.193106) {
	    p = WekaAccelerometerClassifier.N3efe0ce92(i);
	    } 
	    return p;
	  }
	  static double N3efe0ce92(Object []i) {
	    double p = Double.NaN;
	    if (i[12] == null) {
	      p = 1;
	    } else if (((Double) i[12]).doubleValue() <= 1.817792) {
	      p = 1;
	    } else if (((Double) i[12]).doubleValue() > 1.817792) {
	    p = WekaAccelerometerClassifier.N1901b54e3(i);
	    } 
	    return p;
	  }
	  static double N1901b54e3(Object []i) {
	    double p = Double.NaN;
	    if (i[64] == null) {
	      p = 2;
	    } else if (((Double) i[64]).doubleValue() <= 4.573082) {
	      p = 2;
	    } else if (((Double) i[64]).doubleValue() > 4.573082) {
	      p = 1;
	    } 
	    return p;
	  }
	}
