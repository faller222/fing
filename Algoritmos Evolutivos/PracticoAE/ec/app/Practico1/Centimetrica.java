package ec.app.Practico1;
import ec.*;
import ec.simple.*;
import ec.vector.*;

public class Centimetrica extends Problem implements SimpleProblemForm
    {
    public void evaluate(final EvolutionState state,
        final Individual ind,
        final int subpopulation,
        final int threadnum)
        {
        if (ind.evaluated) return;

        if (!(ind instanceof IntegerVectorIndividual))
            state.output.fatal("Whoa!  It's not a IntegerVectorIndividual!!!",null);

        IntegerVectorIndividual ind2 = (IntegerVectorIndividual)ind;

        int desperdicio = 0; //raw fitness
        int current_pizza = 100;
        for(int x=0; x<ind2.genome.length; x++){
            //Si me da lo que queda de pizza
	        if (ind2.genome[x] <= current_pizza){
                //Lo uso
                current_pizza = current_pizza - ind2.genome[x];
            //Si no me da con lo que queda de pizza
            } else {
                //Desperdicio lo que queda
                desperdicio = desperdicio + current_pizza;
                //Agarro una nueva pizza y corto la porcion
                current_pizza = 100 - ind2.genome[x];
            }
    	}
      desperdicio = desperdicio + current_pizza;

        //Para cumplir los requerimientos de simple fitness (0 es el peor y infinito es el mejor, hacemos la cantidad maxima de pizzas menos el desperdicio. A mayor desperdicio menor fitness
        int fitness = ind2.genome.length * 100 - desperdicio;
        boolean ideal = desperdicio == 0;

        if (!(ind2.fitness instanceof SimpleFitness))
            state.output.fatal("Whoa!  It's not a SimpleFitness!!!",null);

        ((SimpleFitness)ind2.fitness).setFitness(state,fitness,ideal);
        ind2.evaluated = true;
        }
    }
