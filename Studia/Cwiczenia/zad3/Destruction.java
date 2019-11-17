

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
                if (connected.contains(item)) {
                    returned.add(possibleSourceOfDestruction);
                    stack.add(possibleSourceOfDestruction);
                }
            });
        }

        return returned.isEmpty() ? null : returned;
    }

    public List<String> destructionPath(String source, String destroyedObject) {
        Stack<String> stack = new Stack<>();
        stack.push(source);
        List<String> paths = new LinkedList<>();

        while (!stack.isEmpty()) {
            String top = stack.pop();
            String topLetter = top;
            if (top.length() > 1) {
                topLetter = top.substring(top.length() - 1);
            }
            if (top.startsWith(source) && top.endsWith(destroyedObject)) {
                paths.add(top);
            }
            Set<String> connectedWithTop = allObjects.get(topLetter);
            if (connectedWithTop != null) {
                connectedWithTop.forEach(el -> stack.push(top + el));
            }
        }
        if (!paths.isEmpty()) {
            return Arrays.asList(paths.get(0).split(""));
        }
        return null;
    }

    public Map<String, Integer> sourceStatistics() {
        Map<String, Integer> returnMap = new HashMap<>();
        allObjects.forEach((k, v) -> {
            Set<String> sources = allObjectsDestroyedBy(k);
            int number = sources == null ? 0 : sources.size();
            returnMap.put(k, number);
        });
        return returnMap;
    }

    public Map<String, Integer> destructionStatistics() {
        Map<String, Integer> returnMap = new HashMap<>();
        allObjects.forEach((k, v) -> {
            Set<String> sources = allSourcesOfDestruction(k);
            int number = sources == null ? 0 : sources.size();
            returnMap.put(k, number);
        });
        return returnMap;
    }
}
