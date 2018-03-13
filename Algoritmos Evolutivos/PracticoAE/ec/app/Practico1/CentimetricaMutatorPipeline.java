
package ec.app.Practico1;
import ec.vector.*;
import ec.*;
import ec.util.*;


import java.util.ArrayList;
import java.util.HashMap;


public class CentimetricaMutatorPipeline extends BreedingPipeline
    {
    public static final String P_OURMUTATION = "our-mutation";

    // We have to specify a default base, even though we never use it
    public Parameter defaultBase() { return VectorDefaults.base().push(P_OURMUTATION); }

    public static final int NUM_SOURCES = 1;

    // Return 1 -- we only use one source
    public int numSources() { return NUM_SOURCES; }

    // We're supposed to create a most _max_ and at least _min_ individuals,
    // drawn from our source and mutated, and stick them into slots in inds[]
    // starting with the slot inds[start].  Let's do this by telling our
    // source to stick those individuals into inds[] and then mutating them
    // right there.
    public int produce(final int min,
        final int max,
        final int subpopulation,
        final ArrayList<Individual> inds,
        final EvolutionState state,
        final int thread, HashMap<String, Object> misc)
        {
        int start = inds.size();

        // grab individuals from our source and stick 'em right into inds.
        // we'll modify them from there
        int n = sources[0].produce(min,max,subpopulation,inds, state,thread, misc);

        // should we bother?
        if (!state.random[thread].nextBoolean(likelihood))
            {
            return n;
            }

        // For efficiency's sake, we assume that all the
        // individuals in inds[] are the same type of individual and that they all
        // share the same common species -- this is a safe assumption because they're
        // all breeding from the same subpopulation.

        // mutate 'em!
        for(int q=start;q<n+start;q++)
            {
            IntegerVectorIndividual i = (IntegerVectorIndividual)inds.get(q);
            //Elegimos dos posiciones distintas al azar
            MersenneTwisterFast twister = new MersenneTwisterFast();
            int genA= twister.nextInt(i.genome.length);
            int genB= twister.nextInt(i.genome.length-1);;
            if (genA <= genB)
            {
                genB++;
            }

            //Intercambio los genes
            int aux = i.genome[genA];
            i.genome[genA] = i.genome[genB];
            i.genome[genB] = aux;

            // it's a "new" individual, so it's no longer been evaluated
            i.evaluated=false;
            }

        return n;
        }

    }
