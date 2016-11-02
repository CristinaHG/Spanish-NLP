import java.nio.file.{Files, Paths}

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

  //read file and transcript to class data List which rows as ["VBD", "VB", "PREVTAG", "TO"]
  def read(path: String, encoding: String, comment: String): Unit = {
    var context=List[List[String]]()

    if (!path.isEmpty) {
      if (path.isInstanceOf[String] && Files.exists(Paths.get(path)) ) {
        //from file path
        val f = scala.io.Source.fromFile(path).getLines().foreach(line => context::=line.split(" ").toList)
        //replace "\" by "" -> codecs.BOMUTF8 not necesary because of codification
        // f = f.map(line => if (f.indexOf(line) == 0 && line.isInstanceOf[String]) line.replaceAll("\"+$", "") else line)
      }else if(path.isInstanceOf[String]){
        //from String
        val f=scala.io.Source.fromString(path).getLines().map(line => context::=line.split(" ").toList)
        //      }else{
        //        //from file or buffer
        //        val f=scala.io.Source.fromBytes(path.toBuffer.toArray,encoding).getLines().map(line => morphology::=line.split(" ").toList)
      }
    }else throw new IllegalArgumentException("a path must be specified")
    this.contextList=context.reverse
    //return morphology
  }

  //Applies contextual rules to the given list of tokens, where each token is a [word,tag] list.
  def apply(tokensTags:List[(String,String)]):List[(String,String)]={
    val o=List(("STAART", "STAART"),("STAART", "STAART"),("STAART", "STAART")) //empty delimiters for look ahead/back

   var t=o.++(tokensTags).++(o)


    var cmd=""
    var x=""
    var y=""

   t.foreach(token=> this.contextList.foreach(r=>
        if((token._2 != "STAART") && (token._2 == r(0) || r(0) == "*")) {
           cmd = r(2).toLowerCase
           x = r(3)
           y = if (r.length > 4) r(4) else ""

          if((cmd=="prevtag" && x==t(t.indexOf(token)-1)._2) ||
          (cmd=="nexttag" && x==t(t.indexOf(token)+1)._2) ||
            (cmd=="prev2tag" && x==t(t.indexOf(token)-2)._2) ||
            (cmd=="next2tag" && x==t(t.indexOf(token)+2)._2) ||
            (cmd=="prev1or2tag" && (  (t(t.indexOf(token)-1)._2, t(t.indexOf(token)-2)._2).toString().contains(x) )) ||
            (cmd=="next1or2tag" && (  (t(t.indexOf(token)+1)._2, t(t.indexOf(token)+2)._2).toString().contains(x) )) ||
            (cmd=="prev1or2or3tag" && ( (t(t.indexOf(token)-1)._2, t(t.indexOf(token)-2)._2,t(t.indexOf(token)-3)._2).toString().contains(x) )) ||
            (cmd=="next1or2or3tag" && ( (t(t.indexOf(token)+1)._2, t(t.indexOf(token)+2)._2,t(t.indexOf(token)+3)._2).toString().contains(x) )) ||
            (cmd=="surroundtag" && ( x==t(t.indexOf(token)-1)._2 && y==t(t.indexOf(token)+1)._2 )) ||
            (cmd=="curwd" && ( x==t(t.indexOf(token))._1 )) ||
            (cmd=="prevwd" && (x==t(t.indexOf(token)-1)._1 )) ||
            (cmd=="nextwd" && (x==t(t.indexOf(token)+1)._1 )) ||
            (cmd=="prev1or2wd" && (t(t.indexOf(token)-1)._1,t(t.indexOf(token)-2)._1).toString().contains(x)) ||
            (cmd=="next1or2wd" && (t(t.indexOf(token)+1)._1,t(t.indexOf(token)+2)._1).toString().contains(x)) ||
            (cmd=="prevwdtag" && (x==t(t.indexOf(token)-1)._1 && y==t(t.indexOf(token)-1)._2 )) ||
            (cmd=="nextwdtag" && (x==t(t.indexOf(token)+1)._1 && y==t(t.indexOf(token)+1)._2 )) ||
            (cmd=="wdprevtag" && (x==t(t.indexOf(token)-1)._2 && y==t(t.indexOf(token))._1 )) ||
            (cmd=="wdnexttag" && (x==t(t.indexOf(token))._1 && y==t(t.indexOf(token)+1)._2 )) ||
            (cmd=="wdand2aft" && (x==t(t.indexOf(token))._1 && y==t(t.indexOf(token)+2)._1 )) ||
            (cmd=="wdand2tagbfr" && (x==t(t.indexOf(token)-2)._2 && y==t(t.indexOf(token))._1 )) ||
            (cmd=="wdand2tagaft" && (x==t(t.indexOf(token))._1 && y==t(t.indexOf(token)+2)._2 )) ||
            (cmd=="lbigram" && (x==t(t.indexOf(token)-1)._1 && y==t(t.indexOf(token))._1 )) ||
            (cmd=="rbigram" && (x==t(t.indexOf(token))._1 && y==t(t.indexOf(token)+1)._1 )) ||
            (cmd=="prevbigram" && (x==t(t.indexOf(token)-2)._2 && y==t(t.indexOf(token)-1)._2 )) ||
            (cmd=="nextbigram" && (x==t(t.indexOf(token)+1)._2 && y==t(t.indexOf(token)+2)._2 ))
          ) t.updated(t.indexOf(token),(t(t.indexOf(token))._1 + "AAHAAAHAHA",r(1)))
        }
    ))
     return t.filter(p=>p._1 != "STAART")
  }

}

