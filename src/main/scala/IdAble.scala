/**
  * Created by Administrator on 20/06/2017.
  */
trait IdAble {

  val id: String
  def nextId(): String


}
object IdAble {
  var idSequence: Int = 0
  def nextIndex(): Int = {
    idSequence += 1
    idSequence
  }
}
