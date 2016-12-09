import java.nio.charset.StandardCharsets
import java.nio.file
import java.nio.file.{Files, Paths}

import com.sun.media.jfxmedia.track.Track.Encoding

import scala.collection.mutable
import scala.io.Source
import scala.reflect.io.File
import scala.xml.Comment

/**
  * Created by cris on 12/10/16.
  */

class Lexicon {

  private[this] var lexiconDict=mutable.Map[String,String]()


  //returns the lexicon dictionary member data
  def getLexDict:mutable.Map[String,String]={
    return this.lexiconDict
  }

  // Returns a dictironary with readed lexicon over the lines in the file at the given path strippping comments
  def read(path: String, encoding: String, comment: String): Unit = {
    //creating empty lexicon dict
    val lexiconDict= mutable.Map[String,String]()

    if (!path.isEmpty) {
      if (path.isInstanceOf[String] && Files.exists(Paths.get(path)) ) {
        //from file path
        val f = scala.io.Source.fromFile(path).getLines().filter(line=>(!line.startsWith(comment))).map{line=>
          val x=line.split(" ")
          (x.head,x.last)}.foreach(u=>lexiconDict+=(u._1->u._2))
      }else if(path.isInstanceOf[String]){
        //from String
              val f=scala.io.Source.fromString(path).getLines().filter(line=>(!line.startsWith(comment))).map{line=>
                val x=line.split(" ")
                (x.head,x.last)}.foreach(u=>lexiconDict+=(u._1->u._2))
      }
    }else throw new IllegalArgumentException("a path must be specified")
    //return lexiconDict
    this.lexiconDict=lexiconDict
  }
}
