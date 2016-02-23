package main

/**
 * Created by julian on 23.02.16.
 */
object db4oTest extends App {

  import com.db4o.Db4oEmbedded

  val connection = Db4oEmbedded.openFile("test.db4o")

}
