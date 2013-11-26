package edu.dartmouth.cs.myruns5;
// Generated with Weka 3.6.10
//
// This code is public domain and comes with no warranty.
//
// Timestamp: Mon Oct 07 17:32:29 PDT 2013



import weka.classifiers.Classifier;
import weka.core.Capabilities;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.RevisionUtils;

public class WekaWrapper extends Classifier {

  /**
   * Returns only the toString() method.
   *
   * @return a string describing the classifier
   */
  public String globalInfo() {
    return toString();
  }

  /**
   * Returns the capabilities of this classifier.
   *
   * @return the capabilities
   */
  public Capabilities getCapabilities() {
    weka.core.Capabilities result = new weka.core.Capabilities(this);

    result.enable(weka.core.Capabilities.Capability.NOMINAL_ATTRIBUTES);
    result.enable(weka.core.Capabilities.Capability.NUMERIC_ATTRIBUTES);
    result.enable(weka.core.Capabilities.Capability.DATE_ATTRIBUTES);
    result.enable(weka.core.Capabilities.Capability.MISSING_VALUES);
    result.enable(weka.core.Capabilities.Capability.NOMINAL_CLASS);
    result.enable(weka.core.Capabilities.Capability.MISSING_CLASS_VALUES);

    result.setMinimumNumberInstances(0);

    return result;
  }

  /**
   * only checks the data against its capabilities.
   *
   * @param i the training data
   */
  public void buildClassifier(Instances i) throws Exception {
    // can classifier handle the data?
    getCapabilities().testWithFail(i);
  }

  /**
   * Classifies the given instance.
   *
   * @param i the instance to classify
   * @return the classification result
   */
  public double classifyInstance(Instance i) throws Exception {
    Object[] s = new Object[i.numAttributes()];
    
    for (int j = 0; j < s.length; j++) {
      if (!i.isMissing(j)) {
        if (i.attribute(j).isNominal())
          s[j] = new String(i.stringValue(j));
        else if (i.attribute(j).isNumeric())
          s[j] = new Double(i.value(j));
      }
    }
    
    // set class value to missing
    s[i.classIndex()] = null;
    
    return WekaClassifier.classify(s);
  }

  /**
   * Returns the revision string.
   * 
   * @return        the revision
   */
  public String getRevision() {
    return RevisionUtils.extract("1.0");
  }

  /**
   * Returns only the classnames and what classifier it is based on.
   *
   * @return a short description
   */
  public String toString() {
    return "Auto-generated classifier wrapper, based on weka.classifiers.trees.J48 (generated with Weka 3.6.10).\n" + this.getClass().getName() + "/WekaClassifier";
  }

  /**
   * Runs the classfier from commandline.
   *
   * @param args the commandline arguments
   */
  public static void main(String args[]) {
    runClassifier(new WekaWrapper(), args);
  }
}

class WekaClassifier {

  public static double classify(Object[] i)
    throws Exception {

    double p = Double.NaN;
    p = WekaClassifier.N7fd08e8e13(i);
    return p;
  }
  static double N7fd08e8e13(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 47.22893) {
    p = WekaClassifier.N553f4e3014(i);
    } else if (((Double) i[0]).doubleValue() > 47.22893) {
    p = WekaClassifier.N7fa6120d16(i);
    } 
    return p;
  }
  static double N553f4e3014(Object []i) {
    double p = Double.NaN;
    if (i[64] == null) {
      p = 0;
    } else if (((Double) i[64]).doubleValue() <= 1.586422) {
      p = 0;
    } else if (((Double) i[64]).doubleValue() > 1.586422) {
    p = WekaClassifier.N2f265d0915(i);
    } 
    return p;
  }
  static double N2f265d0915(Object []i) {
    double p = Double.NaN;
    if (i[8] == null) {
      p = 0;
    } else if (((Double) i[8]).doubleValue() <= 3.301003) {
      p = 0;
    } else if (((Double) i[8]).doubleValue() > 3.301003) {
      p = 2;
    } 
    return p;
  }
  static double N7fa6120d16(Object []i) {
    double p = Double.NaN;
    if (i[64] == null) {
      p = 1;
    } else if (((Double) i[64]).doubleValue() <= 6.664818) {
    p = WekaClassifier.N6feb54f317(i);
    } else if (((Double) i[64]).doubleValue() > 6.664818) {
    p = WekaClassifier.N195e561222(i);
    } 
    return p;
  }
  static double N6feb54f317(Object []i) {
    double p = Double.NaN;
    if (i[64] == null) {
      p = 3;
    } else if (((Double) i[64]).doubleValue() <= 2.880189) {
    p = WekaClassifier.N431656ec18(i);
    } else if (((Double) i[64]).doubleValue() > 2.880189) {
    p = WekaClassifier.N29d45f4720(i);
    } 
    return p;
  }
  static double N431656ec18(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 3;
    } else if (((Double) i[4]).doubleValue() <= 6.840417) {
    p = WekaClassifier.N6fa4c25c19(i);
    } else if (((Double) i[4]).doubleValue() > 6.840417) {
      p = 2;
    } 
    return p;
  }
  static double N6fa4c25c19(Object []i) {
    double p = Double.NaN;
    if (i[7] == null) {
      p = 1;
    } else if (((Double) i[7]).doubleValue() <= 2.583688) {
      p = 1;
    } else if (((Double) i[7]).doubleValue() > 2.583688) {
      p = 3;
    } 
    return p;
  }
  static double N29d45f4720(Object []i) {
    double p = Double.NaN;
    if (i[9] == null) {
      p = 1;
    } else if (((Double) i[9]).doubleValue() <= 7.313052) {
      p = 1;
    } else if (((Double) i[9]).doubleValue() > 7.313052) {
    p = WekaClassifier.N35bb43c521(i);
    } 
    return p;
  }
  static double N35bb43c521(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 10.444708) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() > 10.444708) {
      p = 2;
    } 
    return p;
  }
  static double N195e561222(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 2;
    } else if (((Double) i[0]).doubleValue() <= 860.209014) {
    p = WekaClassifier.N64c4c123(i);
    } else if (((Double) i[0]).doubleValue() > 860.209014) {
      p = 3;
    } 
    return p;
  }
  static double N64c4c123(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 3;
    } else if (((Double) i[0]).doubleValue() <= 231.963254) {
      p = 3;
    } else if (((Double) i[0]).doubleValue() > 231.963254) {
    p = WekaClassifier.N57b15b1a24(i);
    } 
    return p;
  }
  static double N57b15b1a24(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 2;
    } else if (((Double) i[3]).doubleValue() <= 89.870618) {
    p = WekaClassifier.N3cd434f025(i);
    } else if (((Double) i[3]).doubleValue() > 89.870618) {
      p = 2;
    } 
    return p;
  }
  static double N3cd434f025(Object []i) {
    double p = Double.NaN;
    if (i[7] == null) {
      p = 2;
    } else if (((Double) i[7]).doubleValue() <= 29.421053) {
      p = 2;
    } else if (((Double) i[7]).doubleValue() > 29.421053) {
      p = 3;
    } 
    return p;
  }
}
