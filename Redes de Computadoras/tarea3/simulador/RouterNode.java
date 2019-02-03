import javax.swing.*;
import java.util.*;

public class RouterNode {
    private int myID;
    private GuiTextArea myGUI;
    private RouterSimulator sim;

    private HashMap<Integer, Integer> knownCosts;
    private HashMap<Integer, HashMap<Integer, Integer>> distances = new HashMap<>();
    private HashMap<Integer, Integer> salidas = new HashMap<>();
    private HashMap<Integer, Integer> idsPkt = new HashMap<>();

    private int step = 0;
    private int idPkt = 0;

    // --------------------------------------------------
    public RouterNode(int ID, RouterSimulator sim, HashMap<Integer, Integer> costs) {
        myID = ID;
        this.sim = sim;
        myGUI = new GuiTextArea("  Output window for Router #" + ID + "  ");

        // Hago copia local de los costos conocidos
        knownCosts = new HashMap<>(costs);
        //El costo para acceder a mi es 0
        knownCosts.put(myID, 0);
        distances.put(myID, new HashMap<>(costs));
        //Inicializo el vector de rutas
        for (Map.Entry<Integer, Integer> entry : knownCosts.entrySet()) {
            salidas.put(entry.getKey(), entry.getKey());
        }

        HashMap<Integer, Integer> minCost = new HashMap<>(costs);
        //Metadata para identificar solicitudes repetidas
        minCost.put(-1, idPkt);
        minCost.put(-2, myID);

        for (int destId : costs.keySet()) {
            RouterPacket pkt = new RouterPacket(myID, destId, minCost);
            sendUpdate(pkt);
        }

        printDistanceTable();
    }

    // --------------------------------------------------
    private boolean isNewNode(int sourceId) {
        return !idsPkt.containsKey(sourceId);
    }

    private boolean isNewPkt(int sourceId, int idPktRcv) {
        return (idsPkt.get(sourceId) < idPktRcv);
    }

    public void recvUpdate(RouterPacket pkt) {

        int idPktRcv = pkt.mincost.remove(-1);
        int idBridge = pkt.mincost.remove(-2);
        int sourceId = pkt.sourceid;

        boolean forceCalculate = false;

        if (isNewNode(sourceId)) {
            //Recibo informacion de un nodo que no conocia
            idsPkt.put(sourceId, idPktRcv);
            //Guardo la nueva infomacion asociada
            distances.put(sourceId, new HashMap<>(pkt.mincost));
            //Fuerzo a recalcular por que ahora tengo mas informacion
            forceCalculate = true;

            if(knownCosts.containsKey(sourceId)){
                for (int v : pkt.mincost.keySet()) {
                    if (!knownCosts.containsKey(v)) {
                        //Agrego costo y salidas que no conocia del vector nuevo.
                        int cost = knownCosts.get(sourceId) + pkt.mincost.get(v);
                        knownCosts.put(v, cost);
                        salidas.put(v, sourceId);
                    }
                }
            }
        } else if (isNewPkt(sourceId, idPktRcv)) {
            forceCalculate = true;
            idsPkt.put(sourceId, idPktRcv);
            for (Map.Entry<Integer, Integer> entry : pkt.mincost.entrySet()) {
                distances.get(sourceId).put(entry.getKey(), entry.getValue());
            }
        }

        if (forceCalculate) {
            //Esto es para el Flooding
            for (int destId : distances.get(myID).keySet()) {
                if (destId == myID || destId == sourceId || destId == idBridge) {
                    //No envio los datos a mi mismo, ni a quien me los envio
                    continue;
                }
                HashMap<Integer, Integer> p = new HashMap<>(pkt.mincost);
                p.put(-1,idPktRcv);
                p.put(-2, myID);
                sendUpdate(new RouterPacket(sourceId, destId, p));
            }

            dijkstra();
            printDistanceTable();
        }
    }

    // --------------------------------------------------
    private void sendUpdate(RouterPacket pkt) {
        sim.toLayer2(pkt);
    }

    // --------------------------------------------------
    public void printDistanceTable() {
        myGUI.println("Current state for router " + myID + " at time " + sim.getClocktime());
        myGUI.println("Distancetable:\t\t\t\tIter count: " + step++);
        printTable();
        myGUI.println("\nOur distance vector and routes:");
        printHeader();
        printCostAndRoute();
        myGUI.println("\n");
    }

    private void printCostAndRoute() {
        String line = "  cost\t|";
        for (int key : knownCosts.keySet()) {
            if (knownCosts.get(key) == RouterSimulator.INFINITY) {
                line += F.format("inf", 8);
            } else {
                line += F.format(knownCosts.get(key), 8);
            }
        }
        myGUI.println(line);

        String rLine = "  route\t|";
        for (Integer i : salidas.keySet()) {
            if (salidas.get(i) == RouterSimulator.INFINITY) {
                rLine += F.format("-", 8);
            } else {
                rLine += F.format(salidas.get(i), 8);
            }
        }
        myGUI.println(rLine);
    }

    private void printHeader() {
        String header = F.format("     dst\t|", 8);
        for (int key : knownCosts.keySet()) {
            header += F.format(key, 8);
        }
        header += "\n-------------------";
        for (int key : knownCosts.keySet()) {
            header += "--------";
        }

        myGUI.println(header);
    }

    private void printTable() {
        String header = "            |";
        for (int key : knownCosts.keySet()) {
            header += F.format(key, 8);
        }
        header += "\n";
        for (int key : knownCosts.keySet()) {
            header += "----------";
        }
        myGUI.println(header);

        for (Integer i : distances.keySet()) {
            HashMap<Integer, Integer> values = distances.get(i);
            String linenode = F.format(i, 8) + "  |";
            for (int key : knownCosts.keySet()) {
                if (values.containsKey(key)) {
                    if (values.get(key) == RouterSimulator.INFINITY) {
                        linenode += F.format("inf", 8);
                    } else {
                        linenode += F.format(values.get(key), 8);
                    }
                } else {
                    if (key == i) {
                        linenode += F.format(0L, 8);
                    } else {
                        linenode += F.format("-", 8);
                    }
                }
            }
            myGUI.println(linenode);
        }
    }

    // --------------------------------------------------
    // Esto ejecuta al actualizar el costo de un enlace, la red cambió.
    public void updateLinkCost(int dest, int newcost) {

        //Actualizo mi nuevo costo, solo para mi vector, los demas ya se iran enterando
        distances.get(myID).put(dest, newcost);

        HashMap<Integer, Integer> minCost = new HashMap<>(distances.get(myID));
        idPkt++;
        minCost.put(-1, idPkt);
        minCost.put(-2, myID);

        //Solo notifico a mis adyacentes
        for (int destId : distances.get(myID).keySet()) {
            sendUpdate(new RouterPacket(myID, destId, minCost));
        }

        dijkstra();
        printDistanceTable();
    }

    private int getDistance(int idSrc, int idDest) {
        try {
            return distances.get(idSrc).getOrDefault(idDest, RouterSimulator.INFINITY);
        } catch (Exception e) {
            return RouterSimulator.INFINITY;
        }
    }

    private int costoMinimo(int idSrc, int idDest, List<Integer> visitados) {
        //Lo marco como Visitado
        visitados.add(idSrc);
        int result = RouterSimulator.INFINITY;
        int salida = RouterSimulator.INFINITY;

        if (distances.containsKey(idSrc)) {
            for (int i : distances.get(idSrc).keySet()) {
                if (!visitados.contains(i)) {
                    int distSK = getDistance(idSrc, i);

                    if (i != idDest) {
                        distSK += costoMinimo(i, idDest, new ArrayList<>(visitados));
                    }
                    if (result > distSK) {
                        result = distSK;
                        salida = i;
                    }
                }
            }
        }
        if (idSrc == myID) {
            if (result == RouterSimulator.INFINITY && salidas.containsKey(idDest)) {
                salidas.put(idDest, RouterSimulator.INFINITY);
            } else {
                salidas.put(idDest, salida);
            }
        }
        return result;
    }

    private void dijkstra() {
        for (int idDest : knownCosts.keySet()) {
            if (idDest != myID) {
                knownCosts.put(idDest, costoMinimo(myID, idDest, new ArrayList<>()));
            }
        }
    }
}