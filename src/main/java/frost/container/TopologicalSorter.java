package frost.container;


import frost.container.component.Component;
import frost.container.component.ConfigurationComponent;
import frost.container.component.ManagedComponent;

import java.util.*;

public class TopologicalSorter {
    private List<Component> components;
    private Map<Component,List<Component>> adjComponentGraph;


    public TopologicalSorter(List<Component> components) {
        this.components = components;
       adjComponentGraph = buildGraph(components);


    }

    private Map<Component,List<Component>> buildGraph(List<Component> components) {
        Map<Component,List<Component>> adjComponentGraph = new HashMap<>();
        for(Component component : components) {

            if(component instanceof ManagedComponent) {
                if(!adjComponentGraph.containsKey(component)) {
                    adjComponentGraph.put((ManagedComponent)component, new ArrayList<>());
                }
                Class<?>[] dependencies = ((ManagedComponent)component).getDependencies();
                for(Class<?> dependency : dependencies) {
                    Component c = findComponent(dependency);
                    adjComponentGraph.get(component).add(c);
                }
            }else {
                if(!adjComponentGraph.containsKey(component)) {
                    adjComponentGraph.put((ConfigurationComponent)component, new ArrayList<>());

                }
            }
        }

        return adjComponentGraph;
    }

    public List<Component> sort() {
        List<Component> sorted = new ArrayList<Component>();
        Set<Component> visited = new HashSet<>();
        for(Component component : adjComponentGraph.keySet()) {
            if(component instanceof ConfigurationComponent) {
                if (!sorted.contains(component)) {
                    sorted.add(component);
                }
            }else {
                if(!visited.contains(component)) {
                    topSortRecur(visited,component,sorted);
                }
            }
        }

        return sorted;
    }

    private void topSortRecur(Set<Component> visited, Component component, List<Component> sorted) {
             visited.add(component);
             List<Component> dependencies = adjComponentGraph.get(component);
             for(Component dependency : dependencies) {
                 if(!visited.contains(dependency)) {
                     topSortRecur(visited,dependency,sorted);
                 }
             }
             sorted.add(component);

    }

    private Component findComponent(Class<?> classz) {
        for(Component component : components) {
            if(component.getClassz().equals(classz)) {
                return component;
            }
        }
        throw new RuntimeException("Could not topologically sort. " + classz + " Component not found");
    }
}
