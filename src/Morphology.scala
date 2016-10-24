import java.nio.file.{Files, Paths}

import scala.collection.JavaConverters._
import scala.collection.mutable

/**
  * Created by cris on 20/10/16.
  */

//--- MORPHOLOGICAL RULES ---------------------------------------------------------------------------
// Brill's algorithm generates lexical (i.e., morphological) rules in the following format:
// NN s fhassuf 1 NNS x => unknown words ending in -s and tagged NN change to NNS.
//     ly hassuf 2 RB x => unknown words ending in -ly change to RB.
class Morphology {

  val knowLexicon=new Lexicon
  //set of rules, based on word morphology (prefix,suffix)
  var rulesSet= Set(
    "word", // Word is x.
    "char", // Word contains x.
    "haspref", // Word starts with x.
    "hassuf", // Word end with x.
    "addpref", // x + word is in lexicon.
    "addsuf", // Word + x is in lexicon.
    "deletepref", // Word without x at the start is in lexicon.
    "deletesuf", // Word without x at the end is in lexicon.
    "goodleft", // Word preceded by word x.
    "goodright"// Word followed by word x.
  )
  rulesSet.foreach(r=> rulesSet.+=("f"+r.mkString))


  // Returns a list of lists as the result of readed morphology.txt rules
  def read(path: String, encoding: String, comment: String): List[List[String]] = {
    var morphology=List[List[String]]()

    if (!path.isEmpty) {
      if (path.isInstanceOf[String] && Files.exists(Paths.get(path)) ) {
        //from file path
        val f = scala.io.Source.fromFile(path).getLines().foreach(line => morphology::=line.split(" ").toList)
        //replace "\" by "" -> codecs.BOMUTF8 not necesary because of codification
        // f = f.map(line => if (f.indexOf(line) == 0 && line.isInstanceOf[String]) line.replaceAll("\"+$", "") else line)
      }else if(path.isInstanceOf[String]){
        //from String
        val f=scala.io.Source.fromString(path).getLines().map(line => morphology::=line.split(" ").toList)
//      }else{
//        //from file or buffer
//        val f=scala.io.Source.fromBytes(path.toBuffer.toArray,encoding).getLines().map(line => morphology::=line.split(" ").toList)
      }
    }else throw new IllegalArgumentException("a path must be specified")
    return morphology
  }
//Applies lexical rules to the given token, which is a [word, tag] list.
  def apply(token:String,tag:String,previus:(String,String), next:(String,String),morphology:List[List[String]],lexicon:Map[String,String]): Unit ={

    var f = false
    var x =""
    var cmd=""

    morphology.foreach(l=> {
      if (rulesSet.contains(l(1))) { // Rule = ly hassuf 2 RB x
        f = false
        x = l(0)
        val pos = (l.length - 2)
         cmd = l(1).toLowerCase
      }
      if (rulesSet.contains(l(2))) { // Rule = NN s fhassuf 1 NNS x
         f = true
         x = l(1)
        val pos= (l.length -2)
         cmd= l(2).toLowerCase.stripPrefix("f")
      }
      if( f==false || tag.compareTo(l(0))==true){
        if( (cmd=="word" && x==token) ||
          (cmd=="char" && x.contains(token)) ||
          (cmd=="haspref" && token.startsWith(x)) ||
          (cmd=="hassuf" && token.endsWith(x)) ||
          (cmd=="addpref" && lexicon.contains(x+token)) ||
          (cmd=="addsuf" && lexicon.contains(token+x)) ||
          
             )
      }

    })
  }
}
