import java.util.*;

public class Destruction implements DestructionInterface {

    private Map<String, Set<String>> allObjects = new HashMap<>();

    public void allObjects(Set<String> objects) {
        objects.forEach(obj -> allObjects.put(obj, new HashSet<>()));
    }

    public void addDependence(String source, Set<String> dependentSet) {
        Set<String> connections = allObjects.get(source);
        connections.addAll(dependentSet);
        allObjects.put(source, connections);
    }

    public Set<String> allObjectsDestroyedBy(String source) {
        Set<String> destroy = allObjects.get(source);
        Set<String> allObjectsDestroyedBySource = new HashSet<>(destroy);

        Stack<String> stack = new Stack<>();
        stack.addAll(destroy);

        while (!stack.isEmpty()) {
            String topElement = stack.pop();
            Set<String> toBeDestroyed = allObjects.get(topElement);
            allObjectsDestroyedBySource.addAll(toBeDestroyed);
            toBeDestroyed.forEach(stack::push);
        }
        return allObjectsDestroyedBySource.isEmpty() ? null : allObjectsDestroyedBySource;
    }

    public Set<String> allSourcesOfDestruction(String object) {
        Stack<String> stack = new Stack<>();
        stack.add(object);

        Set<String> returned = new HashSet<>();

        while (!stack.isEmpty()) {
            String item = stack.pop();
            allObjects.forEach((possibleSourceOfDestruction, set) -> {
                Set<String> connected = allObjects.get(possibleSourceOfDestruction);
                if(connected.contains(item)){
                    returned.add(possibleSourceOfDestruction);
                    stack.add(possibleSourceOfDestruction);
                }
            });
        }

        return returned.isEmpty() ? null : returned;
    }

    public List<String> destructionPath(String source, String destroyedObject) {
        return null;
    }

    public Map<String, Integer> sourceStatistics() {
        return null;
    }

    public Map<String, Integer> destructionStatistics() {
        return null;
    }
}
