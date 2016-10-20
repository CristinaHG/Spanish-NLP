import java.nio.file.{Files, Paths}

import scala.collection.mutable

/**
  * Created by cris on 20/10/16.
  */

//--- MORPHOLOGICAL RULES ---------------------------------------------------------------------------
// Brill's algorithm generates lexical (i.e., morphological) rules in the following format:
// NN s fhassuf 1 NNS x => unknown words ending in -s and tagged NN change to NNS.
//     ly hassuf 2 RB x => unknown words ending in -ly change to RB.
class Morphology {

  // Returns a list of lists as the result of readed morphology.txt rules
  def read(path: String, encoding: String, comment: String): List[List[String]] = {
    var morphology:List[List[String]]=Nil

    if (!path.isEmpty) {
      if (path.isInstanceOf[String] && Files.exists(Paths.get(path)) ) {
        //from file path
        val f = scala.io.Source.fromFile(path).getLines().map(line => morphology::=line.split(" "))
        //replace "\" by "" -> codecs.BOMUTF8 not necesary because of codification
        // f = f.map(line => if (f.indexOf(line) == 0 && line.isInstanceOf[String]) line.replaceAll("\"+$", "") else line)
      }else if(path.isInstanceOf[String]){
        //from String
        val f=scala.io.Source.fromString(path).getLines().map(line => morphology::=line.split(" "))
      }else{
        //from file or buffer
        val f=scala.io.Source.fromBytes(path.toBuffer.toArray,encoding).getLines().map(line => morphology::=line.split(" "))
      }
    }else throw new IllegalArgumentException("a path must be specified")
    return morphology
  }
}
