import java.util.*;

@SuppressWarnings("unchecked")
public class RouterPacket implements Cloneable {
  int sourceid;       /* id of sending router sending this pkt */
  int destid;         /* id of router to which pkt being sent 
                         (must be an immediate neighbor) */
  HashMap<Integer,Integer> mincost = new HashMap<Integer,Integer>();    /* min cost to nodes*/


  RouterPacket (int sourceID, int destID, HashMap<Integer,Integer> mincosts){
    this.sourceid = sourceID;
    this.destid = destID;
    this.mincost = (HashMap<Integer,Integer>) mincosts.clone();
  }

  public Object clone(){
    try {
      RouterPacket newPkt = (RouterPacket) super.clone();
      newPkt.mincost = (HashMap<Integer,Integer>) newPkt.mincost.clone();
      return newPkt;
    }
    catch(CloneNotSupportedException e){
      System.err.println(e);
      System.exit(1);
    }
    return null;
  }
    
}

