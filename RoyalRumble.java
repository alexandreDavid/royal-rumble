import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

class RoyalName {
  String fullname;
  String characterName;
  String romanNumber; 

  RoyalName (String fullname) {
      this.fullname = fullname;
      int i = fullname.lastIndexOf(" ");
      this.characterName = fullname.substring(0, i);
      this.romanNumber = fullname.substring(i + 1);
  }

  Integer getIntegerFromRoman() {

    Map<String, Integer> decodeOrder = new HashMap<>();
    decodeOrder.put("I", 1);
    decodeOrder.put("V", 2);
    decodeOrder.put("X", 3);
    decodeOrder.put("L", 4);

    Map<Integer, Integer> decode = new HashMap<>();
    decode.put(1, 1);
    decode.put(2, 5);
    decode.put(3, 10);
    decode.put(4, 50);

    String c = String.valueOf(this.romanNumber.charAt(0));
    Integer prev = decodeOrder.get(c);
    Integer tot = decode.get(prev);
    List<Integer> total = new ArrayList<Integer>();
    for (int i = 1 ; i < this.romanNumber.length() ; i++){
      c = String.valueOf(this.romanNumber.charAt(i));
      Integer val = decodeOrder.get(c);
      // If idx n-1 === idx N || idx - 1 N
      if (prev.equals(val) || prev.equals(val + 1)) {
        tot = tot + decode.get(val);
      } else {
        total.add(tot);
        tot = decode.get(val);
      }
      prev = val;
    }
    total.add(tot);

    Integer toto = 0;
    for (int i = 0; i < total.size() - 1; i++) {
      if (total.get(i) < total.get(i+1)) {
        toto = toto - total.get(i);
      } else {
        toto = toto + total.get(i);
      }
    }
    toto = toto + total.get(total.size() - 1);
    System.out.println(toto);
    return toto;
  }
}

public class RoyalRumble {
  public List<String> getSortedList(final List<String> names) {

    List<RoyalName> royalNameList = new ArrayList<RoyalName>() ;
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
