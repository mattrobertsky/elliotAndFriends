/**
  * Created by Administrator on 19/06/2017.
  */
class Item (var availableDate:java.util.Date, var name:String, var cost:Double, final var itemType:String, var quantity:Int){
  private val id = Item.nextID()
  var test = 9





  object Item {

    private var idSequence = 0

     def nextID() = {
      idSequence += 1
      idSequence
    }


  }

}

