import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

class RomanChar {
  public String alpha;
  public Integer index;
  public Integer numVal;

  RomanChar(final String alpha, final Integer index, final Integer numVal) {
    this.alpha = alpha;
    this.index = index;
    this.numVal = numVal;
  }
}

class RomanChars {

  static final List<RomanChar> romanChars = new ArrayList<RomanChar>() {

    private static final long serialVersionUID = 1L;

    {
      add(new RomanChar("I", 1, 1));
      add(new RomanChar("V", 2, 5));
      add(new RomanChar("X", 3, 10));
      add(new RomanChar("L", 4, 50));
    }
  };

  public static RomanChar getByAlpha(String alpha) {
    return romanChars.stream().filter(r -> alpha.equals(r.alpha)).findAny().orElse(null);
  }
}

class RoyalName {
  public String fullname;
  public String characterName;
  public String romanNumber;
  private Integer integerFromRoman;

  RoyalName(final String fullname) {
    this.fullname = fullname;
    final int i = fullname.lastIndexOf(" ");
    if (i != -1) {
      this.characterName = fullname.substring(0, i);
      this.romanNumber = fullname.substring(i + 1);
    } else {
      this.characterName = fullname;
    }
    
  }

  /**
   * 2 differents actions. The first one is to merge numbers per group. A group
   * represents the sum of all the signs in a row equal or just above the previous
   * one in term of index. Example: if XII => first group is X and second is II
   * because X and I are separated of 2 levels. The second is to know if the group
   * has to be added or substracted from the result. For that, we compare each
   * value with the next one. If the next is higher so we substract from the
   * result. Example: XLVII => 3 groups 10, 50 and 7 => 10 < 50 and 50 > 7 so -10
   * + 50 + 7 = 47
   * 
   * @return Integer
   */
  Integer getIntegerFromRoman() {

    // If already calculated not necessary
    if (this.integerFromRoman != null) {
      return this.integerFromRoman;
    }

    if (this.romanNumber == null) {
      return null;
    }

    // First action
    String c = String.valueOf(this.romanNumber.charAt(0));

    RomanChar prev = RomanChars.getByAlpha(c);
    Integer groupSum = prev.numVal;
    List<Integer> allGroups = new ArrayList<Integer>();
    for (int i = 1; i < this.romanNumber.length(); i++) {
      c = String.valueOf(this.romanNumber.charAt(i));
      RomanChar val = RomanChars.getByAlpha(c);
      // If idx n-1 === idx N || idx - 1 N
      if (prev.index.equals(val.index) || prev.index.equals(val.index + 1)) {
        groupSum = groupSum + val.numVal;
      } else {
        allGroups.add(groupSum);
        groupSum = val.numVal;
      }
      prev = val;
    }
    allGroups.add(groupSum);

    // Second action
    this.integerFromRoman = 0;
    for (int i = 0; i < allGroups.size() - 1; i++) {
      if (allGroups.get(i) < allGroups.get(i + 1)) {
        this.integerFromRoman = this.integerFromRoman - allGroups.get(i);
      } else {
        this.integerFromRoman = this.integerFromRoman + allGroups.get(i);
      }
    }
    this.integerFromRoman = this.integerFromRoman + allGroups.get(allGroups.size() - 1);
    return this.integerFromRoman;
  }
}

public class RoyalRumble {
  public List<String> getSortedList(final List<String> names) {

    final List<RoyalName> royalNameList = new ArrayList<RoyalName>();
    names.forEach(name -> royalNameList.add(new RoyalName(name)));

    Collections.sort(royalNameList, (royalName1, royalName2) -> {
      Integer compare = royalName1.characterName.compareTo(royalName2.characterName);
      if (compare == 0) {
        compare = royalName1.getIntegerFromRoman().compareTo(royalName2.getIntegerFromRoman());
      }
      return compare;
    });
    return royalNameList.stream().map(royalName -> royalName.fullname).collect(Collectors.toList());
  }
}
