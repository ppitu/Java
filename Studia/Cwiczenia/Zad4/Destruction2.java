

import java.util.*;
import java.util.stream.Collectors;

public class Destruction2 implements DestructionInterface2 {

    private Map<String, Set<String>> allObjects = new HashMap<>();

    @Override
    public void allObjects(Set<String> objects) {
        objects.forEach(obj -> allObjects.put(obj, new HashSet<>()));
    }

    @Override
    public void addDependence(String source, Set<String> dependentSet)
            throws ObjectUnknownException, LoopException {
        boolean isDependencySetValid = dependentSet.stream()
                .allMatch(dep -> allObjects.containsKey(dep));
        if (!allObjects.containsKey(source) || !isDependencySetValid) {
            throw new ObjectUnknownException();
        }

        for (String entry : dependentSet) {
            Map<String, Set<String>> mapCopy = allObjects.entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, e -> new HashSet<>(e.getValue())));
            Set<String> connections = mapCopy.get(source);
            connections.add(entry);
            mapCopy.put(source, connections);
            checkForLoop(source, mapCopy);
        }
        
        Set<String> connections = allObjects.get(source);
        connections.addAll(dependentSet);
        allObjects.put(source, connections);
    }

    @Override
    public Set<List<String>> allDestructionPath(String source, String destroyedObject)
            throws NoPathException, ObjectUnknownException {
        List<String> destructivePaths = getDestructivePaths(source, destroyedObject);

        if (!destructivePaths.isEmpty()) {
            return destructivePaths.stream()
                    .map(path -> path.split(""))
                    .map(Arrays::asList)
                    .collect(Collectors.toSet());
        }
        throw new NoPathException();
    }

    @Override
    public List<String> shortestDestructionPath(String source, String destroyedObject)
            throws NoPathException, ObjectUnknownException, AmbiguousSolutionsException {
        List<String> destructivePaths = getDestructivePaths(source, destroyedObject);

        if (!destructivePaths.isEmpty()) {
            Set<List<String>> setOfPaths = destructivePaths.stream()
                    .map(path -> path.split(""))
                    .map(Arrays::asList)
                    .collect(Collectors.toSet());
            TreeMap<Integer, List<List<String>>> map = new TreeMap<>();
            setOfPaths.forEach(fullPath -> {
                int size = fullPath.size();
                List<List<String>> paths = map.getOrDefault(size, new ArrayList<>());
                paths.add(fullPath);
                map.put(size, paths);
            });
            if (!map.isEmpty()) {
                Map.Entry<Integer, List<List<String>>> entry = map.firstEntry();
                List<List<String>> lists = entry.getValue();
                if (lists.size() > 1) {
                    throw new AmbiguousSolutionsException();
                }
                return lists.get(0);
            }
        }
        throw new NoPathException();
    }

    private List<String> getDestructivePaths(String source, String destroyedObject)
            throws ObjectUnknownException {

        if (!allObjects.containsKey(source) || !allObjects.containsKey(destroyedObject)) {
            throw new ObjectUnknownException();
        }
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
        return paths;
    }

    private void checkForLoop(String source, Map<String, Set<String>> copyOfMap) throws LoopException {
        Stack<String> stack = new Stack<>();
        stack.push(source);

        while (!stack.isEmpty()) {
            String top = stack.pop();
            String topLetter = top;
            if (top.length() > 1) {
                topLetter = top.substring(top.length() - 1);
            }
            if (top.length() >1 && top.startsWith(source) && top.endsWith(source)) {
                throw new LoopException();
            }
            Set<String> connectedWithTop = copyOfMap.get(topLetter);
            if (connectedWithTop != null) {
                connectedWithTop.forEach(el -> stack.push(top + el));
            }
        }
    }
}
