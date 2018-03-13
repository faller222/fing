/*
  Copyright 2006 by Sean Luke
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
*/


package ec.app.Practico1;

import ec.vector.*;
import ec.*;
import ec.util.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;



public class CentimetricaCrossoverPipeline extends BreedingPipeline
    {
    public static final String P_TOSS = "toss";
    public static final String P_CROSSOVER = "xover";
    public static final int NUM_SOURCES = 2;
    public static final String KEY_PARENTS = "parents";

    /** Should the pipeline discard the second parent after crossing over? */
    public boolean tossSecondParent;

    /** Temporary holding place for parents */
    ArrayList<Individual> parents;

    public CentimetricaCrossoverPipeline()
        {
        // by Ermo. get rid of asList
        //parents = new ArrayList<Individual>(Arrays.asList(new VectorIndividual[2]));;
        parents = new ArrayList<Individual>();
        }
    public Parameter defaultBase() { return VectorDefaults.base().push(P_CROSSOVER); }

    /** Returns 2 */
    public int numSources() { return NUM_SOURCES; }

    public Object clone()
        {
        CentimetricaCrossoverPipeline c = (CentimetricaCrossoverPipeline)(super.clone());

        // deep-cloned stuff
        c.parents = new ArrayList<Individual>(parents);

        return c;
        }

    public void setup(final EvolutionState state, final Parameter base)
        {
        super.setup(state,base);
        Parameter def = defaultBase();
        tossSecondParent = state.parameters.getBoolean(base.push(P_TOSS),
            def.push(P_TOSS),false);
        }

    /** Returns 2 * minimum number of typical individuals produced by any sources, else
        1* minimum number if tossSecondParent is true. */
    public int typicalIndsProduced()
        {
        return (tossSecondParent? minChildProduction(): minChildProduction()*2);
        }

    public int produce(final int min,
        final int max,
        final int subpopulation,
        final ArrayList<Individual> inds,
        final EvolutionState state,
        final int thread, HashMap<String, Object> misc)

        {
        int start = inds.size();

        // how many individuals should we make?
        int n = typicalIndsProduced();
        if (n < min) n = min;
        if (n > max) n = max;

        IntBag[] parentparents = null;
        IntBag[] preserveParents = null;

        if (misc!=null && misc.containsKey(KEY_PARENTS))
            {
            preserveParents = (IntBag[])misc.get(KEY_PARENTS);
            parentparents = new IntBag[2];
            misc.put(KEY_PARENTS, parentparents);
            }


        // should we use them straight?
        if (!state.random[thread].nextBoolean(likelihood))
            {
            // just load from source 0 and clone 'em
            sources[0].produce(n,n,subpopulation,inds, state,thread,misc);
            return n;
            }


        for(int q=start;q<n+start; /* no increment */)  // keep on going until we're filled up
            {
            parents.clear();

            // grab two individuals from our sources
            if (sources[0]==sources[1])  // grab from the same source
                {
                sources[0].produce(2,2,subpopulation, parents, state,thread, misc);
                }
            else // grab from different sources
                {
                sources[0].produce(1,1,subpopulation, parents, state,thread, misc);
                sources[1].produce(1,1,subpopulation, parents, state,thread, misc);
                }



            // at this point, parents[] contains our two selected individuals,
            // AND they're copied so we own them and can make whatever modifications
            // we like on them.

            // so we'll cross them over now.  Since this is the default pipeline,
            // we'll just do it by calling defaultCrossover on the first child

            //((VectorIndividual)(parents.get(0))).defaultCrossover(state,thread,((VectorIndividual)(parents.get(1))));
            //Comienza el cruzamiento

                int indSize=Integer.valueOf(parents.get(0).size()+"");
                //Podria sorterse, pero lo cortamos al medio
                 Long middle = (long) Math.floor(indSize / 2);

                try{
                  int[] p1 =(int[]) ((IntegerVectorIndividual)parents.get(0)).getGenome();
                  int[] p2 =(int[]) ((IntegerVectorIndividual)parents.get(1)).getGenome();
                  int[] h1 = new int[ indSize ];
                  int[] h2 = new int[ indSize ];
                  //para cada elemento del padre
                  for (int x=0; x<indSize ; x++) {
                    int gen = p1[x];
                    if (x<middle){
                      h1[x]=gen;
                      //Busco el mismo elemento en el otro padre
                      for (int y=0; y<indSize ; y++) {
                        //encuentro el primero y lo marco negativo
                        if(p2[y]==gen){
                          p2[y]=-p2[y];
                          break;
                        }
                      }
                    }else{
                      h2[x]=gen;
                    }
                  }
                  int i1=Integer.valueOf(middle+"");
                  int i2=0;
                  for (int y=0; y<indSize ; y++) {
int gen=0;
                    try{
                         gen = p2[y];
                      }catch(ArrayIndexOutOfBoundsException e){
                    System.out.println(p2);
                        throw e;
                      }
                    if (gen<0){
                      h2[i2]=-gen;
                      i2++;
                    }else{
                      try{
                      h1[i1]=gen;
                    }catch(ArrayIndexOutOfBoundsException e){
                  System.err.println(indSize+" IND "+ i1);
                  System.err.println("Size "+ h1.length);
                      throw e;
                    }
                      i1++;
                    }
                  }

                    ((IntegerVectorIndividual)parents.get(0)).setGenome(h1);
                    ((IntegerVectorIndividual)parents.get(1)).setGenome(h2);
                }catch(ArrayIndexOutOfBoundsException e){

                  throw e;
                }


            parents.get(0).evaluated=false;
            parents.get(1).evaluated=false;

            // add 'em to the population
            // by Ermo. this should use add instead of set, because the inds is empty, so will throw index out of bounds
            // okay -- Sean
            inds.add(parents.get(0));
            if (preserveParents != null)
                {
                parentparents[0].addAll(parentparents[1]);
                preserveParents[q] = parentparents[0];
                }
            q++;
            if (q<n+start && !tossSecondParent)
                {
                // by Ermo. as as here, see the comments above
                inds.add(parents.get(1));
                if (preserveParents != null)
                    {
                    preserveParents[q] = new IntBag(parentparents[0]);
                    }
                q++;
                }
            }

        if (preserveParents != null)
            {
            misc.put(KEY_PARENTS, preserveParents);
            }
        return n;
        }




    }
