/**
  * Created by cris on 26/10/16.
  */
class Context {
  // Brill's algorithm generates contextual rules in the following format:
  // VBD VB PREVTAG TO => unknown word tagged VBD changes to VB if preceded by a word tagged TO.
  var contextList=List[List[String]]()

  //a set of rles based on the preceding and following words(context)
  var rulesSet= Set(
    "prevtag", // Preceding word is tagged x.
    "nexttag", // Following word is tagged x.
    "prev2tag", // Word 2 before is tagged x.
    "next2tag", // Word 2 after is tagged x.
    "prev1or2tag", // One of 2 preceding words is tagged x.
    "next1or2tag", // One of 2 following words is tagged x.
    "prev1or2or3tag", // One of 3 preceding words is tagged x.
    "next1or2or3tag", // One of 3 following words is tagged x.
    "surroundtag", // Preceding word is tagged x and following word is tagged y.
    "curwd", // Current word is x.
    "prevwd", // Preceding word is x.
    "nextwd", // Following word is x.
    "prev1or2wd", // One of 2 preceding words is x.
    "next1or2wd", // One of 2 following words is x.
    "next1or2or3wd", // One of 3 preceding words is x.
    "prev1or2or3wd", // One of 3 following words is x.
    "prevwdtag", // Preceding word is x and tagged y.
    "nextwdtag", // Following word is x and tagged y.
    "wdprevtag", // Current word is y and preceding word is tagged x.
    "wdnexttag", // Current word is x and following word is tagged y.
    "wdand2aft", // Current word is x and word 2 after is y.
    "wdand2tagbfr", // Current word is y and word 2 before is tagged x.
    "wdand2tagaft", // Current word is x and word 2 after is tagged y.
    "lbigram", // Current word is y and word before is x.
    "rbigram", // Current word is x and word after is y.
    "prevbigram", // Preceding word is tagged x and word before is tagged y.
    "nextbigram" // Following word is tagged x and word after is tagged y.
  )

}
