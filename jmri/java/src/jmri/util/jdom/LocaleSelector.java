// LocaleSelector.java

package jmri.util.jdom;

import org.jdom.*;
import java.util.Locale;

/**
 * Select XML content based on current Locale.
 *
 * Tries: e.g. jp_JP, then jp, then nothing.
 * 
 * _tlh is treated as a special case, for
 * the ant locale target
 *
 * @author Bob Jacobsen  Copyright 2010
 * @since 2.9.3
 * @version $Revision: 21933 $
 */

public class LocaleSelector {
    static String[] suffixes 
            = new String[] {
                Locale.getDefault().getLanguage()+"_"+Locale.getDefault().getCountry(),
                Locale.getDefault().getLanguage()                              
            };
    
    static boolean testLocale = Locale.getDefault().getLanguage().equals("tlh");
    
    /**
     * Return the value of an attribute
     * for the current locale.
     *
     * <foo temp="a">
     *   <temp xml:lang="hh">b</temp>
     * </foo>
     *
     * Say it's looking for the attribute ATT. 
     * For each possible suffix,
     *   it first looks for a contained element
     *      named ATT
     *      with an XML 'lang' attribute for the suffix.
     *   If so, it takes that value.
     * If none are found, the attribute value is taken
     *   from the original element
     */
    static public String getAttribute(Element el, String name) {
        String retval;
        if (testLocale) {
            Attribute a = el.getAttribute(name);
            if (a == null) return null;
            return a.getValue().toUpperCase();
        }
        // look for each suffix first
        for (String suffix : suffixes) {
            retval = checkElement(el, name, suffix);
            if (retval != null) return retval;
        }
        
        // failed, go back to original attribute
        Attribute a = el.getAttribute(name);
        if (a == null) return null;
        return a.getValue();
    }
    
    /**
      * checks one element to see if it's the one for the current language
      * else returns null
      */
    static String checkElement(Element el, String name, String suffix) {
        for (Object obj : el.getChildren(name)) {
            Element e = (Element)obj;
            Attribute a = e.getAttribute("lang", Namespace.XML_NAMESPACE);
            if (a != null) {
                if (a.getValue().equals(suffix)) {
                    return e.getText();
                }
            }
        }
        return null;        
    }

}