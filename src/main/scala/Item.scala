/**
  * Created by Administrator on 19/06/2017.
  */
class Item (var availableDate:String, var name:String, var cost:Double, var itemType:String){
  private val id = Item.nextID()


  object Item {

    private var idSequence = 0

    def nextID() = {
      idSequence += 1
      idSequence
    }

  }
}

