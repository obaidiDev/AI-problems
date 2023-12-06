package ICS381.HW1and2;

import java.util.*;

public class mTilePuzzleSolver {
    private final TreeNode<int[][]> initialState = new TreeNode<>();
    private final TreeNode<int[][]> goal = new TreeNode<>();
    private final int n;
    private final Stack<TreeNode<int[][]>> solutionPath;
    private int numberOfVisitedNodes;
    private int numberOfStoredNodes;
    mTilePuzzleSolver(int[][] initialState, int[][] goal){
        this.n = initialState[0].length;
        if (isSolvable(initialState, n))
            throw new RuntimeException("Unsolvable state");
        this.initialState.data = initialState;
        this.initialState.depth = 0;
        this.goal.data = goal;
        numberOfVisitedNodes = 0;
        numberOfStoredNodes = 0;
        solutionPath = new Stack<>();
        System.out.println(Arrays.deepToString(initialState));
    }
    public void BFS(){
        Queue<TreeNode<int[][]>> queue = new LinkedList<>();
        setInitialStateHole();
        queue.add(initialState);
        TreeNode<int[][]> removedNode = initialState;
        int[][] newState;
        int i, j;
        while (!Arrays.deepEquals(removedNode.data, goal.data)){
            numberOfVisitedNodes++;
            removedNode = queue.remove();
            i = removedNode.hole[0];
            j = removedNode.hole[1];
            // Move hole up.
            if(i > 0 && !removedNode.origin.equals("up")) {
                newState = createNewState(removedNode.data, i, j, i - 1, j);
                removedNode.firstChild = new TreeNode<>(newState, new int[]{i-1,j}, removedNode,"down");
                queue.add(removedNode.firstChild);
            }
            // Move hole down.
            if(i < n - 1 && !removedNode.origin.equals("down")) {
                newState = createNewState(removedNode.data, i, j, i + 1, j);
                TreeNode<int[][]> newNode = new TreeNode<>(newState, new int[]{i+1,j}, removedNode, "up");
                if (removedNode.firstChild == null)
                    removedNode.firstChild = newNode;
                else
                    removedNode.firstChild.nextSibling = newNode;
                queue.add(newNode);
            }
            // Move hole to right.
            if(j < n - 1 && !removedNode.origin.equals("right")) {
                newState = createNewState(removedNode.data, i, j, i, j + 1);
                TreeNode<int[][]> newNode = new TreeNode<>(newState, new int[]{i,j + 1}, removedNode, "left");
                if (removedNode.firstChild == null)
                    removedNode.firstChild = newNode;
                else if (removedNode.firstChild.nextSibling == null)
                    removedNode.firstChild.nextSibling = newNode;
                else
                    removedNode.firstChild.nextSibling.nextSibling = newNode;
                queue.add(newNode);
            }
            // Move hole to left.
            if(j > 0 && !removedNode.origin.equals("left")) {
                newState = createNewState(removedNode.data, i, j, i, j - 1);
                TreeNode<int[][]> newNode = new TreeNode<>(newState, new int[]{i,j - 1}, removedNode, "right");
                if (removedNode.firstChild == null)
                    removedNode.firstChild = newNode;
                else if (removedNode.firstChild.nextSibling == null)
                    removedNode.firstChild.nextSibling = newNode;
                else if (removedNode.firstChild.nextSibling.nextSibling == null)
                    removedNode.firstChild.nextSibling.nextSibling = newNode;
                else
                    removedNode.firstChild.nextSibling.nextSibling.nextSibling = newNode;
                queue.add(newNode);
            }
        }
        while (removedNode.parent != null){
            solutionPath.add(removedNode);
            removedNode = removedNode.parent;
        }
        numberOfStoredNodes = queue.size();
    }
    public void iterativeDeepening(){
        Stack<TreeNode<int[][]>> stack = new Stack<>();
        setInitialStateHole();
        stack.add(initialState);
        TreeNode<int[][]> removedNode = initialState;
        int[][] newState;
        int i, j;
        int depthLimit = 20; // Based on the average depth for BFS
        int depth;
        while (!Arrays.deepEquals(removedNode.data, goal.data)) {
            if (stack.isEmpty()) {
                stack.add(initialState);
                depthLimit += depthLimit;
                numberOfVisitedNodes = 0;
            }
            removedNode = stack.remove();
            depth = removedNode.depth + 1;
            i = removedNode.hole[0];
            j = removedNode.hole[1];
            numberOfVisitedNodes++;
            if (depth <= depthLimit) {
                // Move hole up.
                if (i > 0 && !removedNode.origin.equals("up")) {
                    newState = createNewState(removedNode.data, i, j, i - 1, j);
                    removedNode.firstChild = new TreeNode<>(newState, new int[]{i - 1, j}, removedNode, "down", depth);
                    stack.add(removedNode.firstChild);
                }
                // Move hole down.
                if (i < n - 1 && !removedNode.origin.equals("down")) {
                    newState = createNewState(removedNode.data, i, j, i + 1, j);
                    TreeNode<int[][]> newNode = new TreeNode<>(newState, new int[]{i + 1, j}, removedNode, "up", depth);
                    if (removedNode.firstChild == null)
                        removedNode.firstChild = newNode;
                    else
                        removedNode.firstChild.nextSibling = newNode;
                    stack.add(newNode);
                }
                // Move hole to right.
                if (j < n - 1 && !removedNode.origin.equals("right")) {
                    newState = createNewState(removedNode.data, i, j, i, j + 1);
                    TreeNode<int[][]> newNode = new TreeNode<>(newState, new int[]{i, j + 1}, removedNode, "left", depth);
                    if (removedNode.firstChild == null)
                        removedNode.firstChild = newNode;
                    else if (removedNode.firstChild.nextSibling == null)
                        removedNode.firstChild.nextSibling = newNode;
                    else
                        removedNode.firstChild.nextSibling.nextSibling = newNode;
                    stack.add(newNode);
                }
                // Move hole to left.
                if (j > 0 && !removedNode.origin.equals("left")) {
                    newState = createNewState(removedNode.data, i, j, i, j - 1);
                    TreeNode<int[][]> newNode = new TreeNode<>(newState, new int[]{i, j - 1}, removedNode, "right", depth);
                    if (removedNode.firstChild == null)
                        removedNode.firstChild = newNode;
                    else if (removedNode.firstChild.nextSibling == null)
                        removedNode.firstChild.nextSibling = newNode;
                    else if (removedNode.firstChild.nextSibling.nextSibling == null)
                        removedNode.firstChild.nextSibling.nextSibling = newNode;
                    else
                        removedNode.firstChild.nextSibling.nextSibling.nextSibling = newNode;
                    stack.add(newNode);
                }
            }
        }
        while (removedNode.parent != null){
            solutionPath.add(removedNode);
            removedNode = removedNode.parent;
        }
        numberOfStoredNodes = stack.size;
    }
    public void DFSWithRevisitCheck(){
        Stack<TreeNode<int[][]>> stack = new Stack<>();
        setInitialStateHole();
        stack.add(initialState);
        TreeNode<int[][]> removedNode = initialState;
        int[][] newState;
        Set<String> visited = new HashSet<>();
        int i, j;
        while (!Arrays.deepEquals(removedNode.data, goal.data)){
            removedNode = stack.remove();
            if (!visited.add(Arrays.deepToString(removedNode.data)))
                continue;
            i = removedNode.hole[0];
            j = removedNode.hole[1];
            numberOfVisitedNodes++;
            // Move hole up.
            if(i > 0 && !removedNode.origin.equals("up")) {
                newState = createNewState(removedNode.data, i, j, i - 1, j);
                removedNode.firstChild = new TreeNode<>(newState, new int[]{i-1,j}, removedNode,"down");
                stack.add(removedNode.firstChild);
            }
            // Move hole down.
            if(i < n - 1 && !removedNode.origin.equals("down")) {
                newState = createNewState(removedNode.data, i, j, i + 1, j);
                TreeNode<int[][]> newNode = new TreeNode<>(newState, new int[]{i+1,j}, removedNode, "up");
                if (removedNode.firstChild == null)
                    removedNode.firstChild = newNode;
                else
                    removedNode.firstChild.nextSibling = newNode;
                stack.add(newNode);
            }
            // Move hole to right.
            if(j < n - 1 && !removedNode.origin.equals("right")) {
                newState = createNewState(removedNode.data, i, j, i, j + 1);
                TreeNode<int[][]> newNode = new TreeNode<>(newState, new int[]{i,j + 1}, removedNode, "left");
                if (removedNode.firstChild == null)
                    removedNode.firstChild = newNode;
                else if (removedNode.firstChild.nextSibling == null)
                    removedNode.firstChild.nextSibling = newNode;
                else
                    removedNode.firstChild.nextSibling.nextSibling = newNode;
                stack.add(newNode);
            }
            // Move hole to left.
            if(j > 0 && !removedNode.origin.equals("left")) {
                newState = createNewState(removedNode.data, i, j, i, j - 1);
                TreeNode<int[][]> newNode = new TreeNode<>(newState, new int[]{i,j - 1}, removedNode, "right");
                if (removedNode.firstChild == null)
                    removedNode.firstChild = newNode;
                else if (removedNode.firstChild.nextSibling == null)
                    removedNode.firstChild.nextSibling = newNode;
                else if (removedNode.firstChild.nextSibling.nextSibling == null)
                    removedNode.firstChild.nextSibling.nextSibling = newNode;
                else
                    removedNode.firstChild.nextSibling.nextSibling.nextSibling = newNode;
                stack.add(newNode);
            }
        }
        while (removedNode.parent != null){
            solutionPath.add(removedNode);
            removedNode = removedNode.parent;
        }
        numberOfStoredNodes = stack.size;
    }
    // DFS will not find the solution that is why it is not called.
    public void DFS(){
        Stack<TreeNode<int[][]>> stack = new Stack<>();
        setInitialStateHole();
        stack.add(initialState);
        TreeNode<int[][]> removedNode = initialState;
        int[][] newState;
        int i, j;
        while (!Arrays.deepEquals(removedNode.data, goal.data)){
            removedNode = stack.remove();
            i = removedNode.hole[0];
            j = removedNode.hole[1];
            numberOfVisitedNodes++;
            // Move hole up.
            if(i > 0 && !removedNode.origin.equals("up")) {
                newState = createNewState(removedNode.data, i, j, i - 1, j);
                removedNode.firstChild = new TreeNode<>(newState, new int[]{i-1,j}, removedNode,"down");
                stack.add(removedNode.firstChild);
            }
            // Move hole down.
            if(i < n - 1 && !removedNode.origin.equals("down")) {
                newState = createNewState(removedNode.data, i, j, i + 1, j);
                TreeNode<int[][]> newNode = new TreeNode<>(newState, new int[]{i+1,j}, removedNode, "up");
                if (removedNode.firstChild == null)
                    removedNode.firstChild = newNode;
                else
                    removedNode.firstChild.nextSibling = newNode;
                stack.add(newNode);
            }
            // Move hole to right.
            if(j < n - 1 && !removedNode.origin.equals("right")) {
                newState = createNewState(removedNode.data, i, j, i, j + 1);
                TreeNode<int[][]> newNode = new TreeNode<>(newState, new int[]{i,j + 1}, removedNode, "left");
                if (removedNode.firstChild == null)
                    removedNode.firstChild = newNode;
                else if (removedNode.firstChild.nextSibling == null)
                    removedNode.firstChild.nextSibling = newNode;
                else
                    removedNode.firstChild.nextSibling.nextSibling = newNode;
                stack.add(newNode);
            }
            // Move hole to left.
            if(j > 0 && !removedNode.origin.equals("left")) {
                newState = createNewState(removedNode.data, i, j, i, j - 1);
                TreeNode<int[][]> newNode = new TreeNode<>(newState, new int[]{i,j - 1}, removedNode, "right");
                if (removedNode.firstChild == null)
                    removedNode.firstChild = newNode;
                else if (removedNode.firstChild.nextSibling == null)
                    removedNode.firstChild.nextSibling = newNode;
                else if (removedNode.firstChild.nextSibling.nextSibling == null)
                    removedNode.firstChild.nextSibling.nextSibling = newNode;
                else
                    removedNode.firstChild.nextSibling.nextSibling.nextSibling = newNode;
                stack.add(newNode);
            }
        }
        while (removedNode.parent != null){
            solutionPath.add(removedNode);
            removedNode = removedNode.parent;
        }
        numberOfStoredNodes = stack.size;
    }
    public void aStar(String heuristic){
        PriorityQ queue = new PriorityQ(n, heuristic);
        setInitialStateHole();
        initialState.cost = cost(initialState.data,heuristic);
        queue.add(initialState,initialState.cost);
        TreeNode<int[][]> removedNode = initialState;
        int[][] newState;
        int i, j;
        long limit = recfact(1, (long) n * n);
        limit /= 2;
        int depth,newNodeCost; // depth is h(n) = newNodeCost, g(n) = depth
        while (!Arrays.deepEquals(removedNode.data, goal.data) && numberOfVisitedNodes <= limit){
            numberOfVisitedNodes++;
            removedNode = queue.remove();
            depth = removedNode.depth + 1;
            i = removedNode.hole[0];
            j = removedNode.hole[1];
            // Move hole up.
            if(i > 0 && !removedNode.origin.equals("up")) {
                newState = createNewState(removedNode.data, i, j, i - 1, j);
                newNodeCost = depth + cost(newState,heuristic);
                removedNode.firstChild = new TreeNode<>(newState, new int[]{i-1,j}, removedNode,"down", newNodeCost);
                queue.add(removedNode.firstChild,newNodeCost);
            }
            // Move hole down.
            if(i < n - 1 && !removedNode.origin.equals("down")) {
                newState = createNewState(removedNode.data, i, j, i + 1, j);
                newNodeCost = depth + cost(newState,heuristic);
                TreeNode<int[][]> newNode = new TreeNode<>(newState, new int[]{i+1,j}, removedNode, "up", newNodeCost);
                if (removedNode.firstChild == null)
                    removedNode.firstChild = newNode;
                else
                    removedNode.firstChild.nextSibling = newNode;
                queue.add(newNode,newNodeCost);
            }
            // Move hole to right.
            if(j < n - 1 && !removedNode.origin.equals("right")) {
                newState = createNewState(removedNode.data, i, j, i, j + 1);
                newNodeCost = depth + cost(newState,heuristic);
                TreeNode<int[][]> newNode = new TreeNode<>(newState, new int[]{i,j + 1}, removedNode, "left",newNodeCost);
                if (removedNode.firstChild == null)
                    removedNode.firstChild = newNode;
                else if (removedNode.firstChild.nextSibling == null)
                    removedNode.firstChild.nextSibling = newNode;
                else
                    removedNode.firstChild.nextSibling.nextSibling = newNode;
                queue.add(newNode,newNodeCost);
            }
            // Move hole to left.
            if(j > 0 && !removedNode.origin.equals("left")) {
                newState = createNewState(removedNode.data, i, j, i, j - 1);
                newNodeCost = depth + cost(newState,heuristic);
                TreeNode<int[][]> newNode = new TreeNode<>(newState, new int[]{i,j - 1}, removedNode, "right",newNodeCost);
                if (removedNode.firstChild == null)
                    removedNode.firstChild = newNode;
                else if (removedNode.firstChild.nextSibling == null)
                    removedNode.firstChild.nextSibling = newNode;
                else if (removedNode.firstChild.nextSibling.nextSibling == null)
                    removedNode.firstChild.nextSibling.nextSibling = newNode;
                else
                    removedNode.firstChild.nextSibling.nextSibling.nextSibling = newNode;
                queue.add(newNode,newNodeCost);
            }
        }
        while (removedNode.parent != null){
            solutionPath.add(removedNode);
            removedNode = removedNode.parent;
        }
        numberOfStoredNodes = queue.length;
    }
    public void AStar(String heuristic){
        BinaryHeap queue = new BinaryHeap(n,heuristic,goal.data);
        setInitialStateHole();
        initialState.cost = cost(initialState.data,heuristic);
        queue.add(initialState);
        TreeNode<int[][]> removedNode = initialState;
        int[][] newState;
        int i, j;
        long limit = recfact(1, (long) n * n);
        limit /= 2;
        int depth;
        while (!Arrays.deepEquals(removedNode.data, goal.data) && numberOfVisitedNodes <= limit){
            numberOfVisitedNodes++;
            removedNode = queue.remove();
            depth = removedNode.depth + 1;
            i = removedNode.hole[0];
            j = removedNode.hole[1];
            // Move hole up.
            if(i > 0 && !removedNode.origin.equals("up")) {
                newState = createNewState(removedNode.data, i, j, i - 1, j);
                removedNode.firstChild = new TreeNode<>(newState, new int[]{i-1,j}, removedNode,"down",depth);
                queue.add(removedNode.firstChild);
            }
            // Move hole down.
            if(i < n - 1 && !removedNode.origin.equals("down")) {
                newState = createNewState(removedNode.data, i, j, i + 1, j);
                TreeNode<int[][]> newNode = new TreeNode<>(newState, new int[]{i+1,j}, removedNode, "up", depth);
                if (removedNode.firstChild == null)
                    removedNode.firstChild = newNode;
                else
                    removedNode.firstChild.nextSibling = newNode;
                queue.add(newNode);
            }
            // Move hole to right.
            if(j < n - 1 && !removedNode.origin.equals("right")) {
                newState = createNewState(removedNode.data, i, j, i, j + 1);
                TreeNode<int[][]> newNode = new TreeNode<>(newState, new int[]{i,j + 1}, removedNode, "left",depth);
                if (removedNode.firstChild == null)
                    removedNode.firstChild = newNode;
                else if (removedNode.firstChild.nextSibling == null)
                    removedNode.firstChild.nextSibling = newNode;
                else
                    removedNode.firstChild.nextSibling.nextSibling = newNode;
                queue.add(newNode);
            }
            // Move hole to left.
            if(j > 0 && !removedNode.origin.equals("left")) {
                newState = createNewState(removedNode.data, i, j, i, j - 1);
                TreeNode<int[][]> newNode = new TreeNode<>(newState, new int[]{i,j - 1}, removedNode, "right",depth);
                if (removedNode.firstChild == null)
                    removedNode.firstChild = newNode;
                else if (removedNode.firstChild.nextSibling == null)
                    removedNode.firstChild.nextSibling = newNode;
                else if (removedNode.firstChild.nextSibling.nextSibling == null)
                    removedNode.firstChild.nextSibling.nextSibling = newNode;
                else
                    removedNode.firstChild.nextSibling.nextSibling.nextSibling = newNode;
                queue.add(newNode);
            }
        }
        while (removedNode.parent != null){
            solutionPath.add(removedNode);
            removedNode = removedNode.parent;
        }
        numberOfStoredNodes = queue.count;
    }


    public long recfact(long start, long n) {
        long i;
        if (n <= 16) {
            long r = start;
            for (i = start + 1; i < start + n; i++) r *= i;
            return r;
        }
        i = n / 2;
        return recfact(start, i) * recfact(start + i, n - i);
    }
    private int cost(int[][] currentState, String heuristic){
        return switch (heuristic) {
            case "manhattan" -> manhattanDistance(currentState);
            case "misplaced" -> misplaced(currentState);
            case "euclidean" -> (int) euclideanDistance(currentState);
            default -> throw new RuntimeException("This heuristic function is not supported yet");
        };
    }
    private int misplaced(int[][] currentState) {
        int misplacement = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (currentState[i][j] != goal.data[i][j])
                    misplacement++;
        return misplacement;
    }
    private int manhattanDistance(int[][] currentState){
        int distance = 0, gi, gj;
        for (int i = 0; i < n; i++)
            for(int j = 0; j < n; j++) {
                gi = find(currentState[i][j])[0];
                gj = find(currentState[i][j])[1];
                distance += Math.abs(gi-i) + Math.abs(gj - j);
            }
        return distance;
    }
    private double euclideanDistance(int[][] currentState){
        double distance = 0, gi, gj;
        for (int i = 0; i < n; i++)
            for(int j = 0; j < n; j++) {
                gi = find(currentState[i][j])[0];
                gj = find(currentState[i][j])[1];
                distance += Math.sqrt((gi-i)*(gi-i) + (gj - j)*(gj - j));
            }
        return distance;
    }
    //this method returns the column and row for an element in goal state.
    private int[] find(int x){
        for (int i = 0; i < n; i++)
            for(int j = 0; j < n; j++)
                if (goal.data[i][j] == x)
                    return new int[]{i, j};
        throw new RuntimeException("Element does not exist");
    }
    private void setInitialStateHole(){
        boolean hole = false;
        int i, j = 0;
        for (i = 0; !hole && i < n; i++)
            for (j = 0; !hole && j < n; j++)
                hole = this.initialState.data[i][j] == 0;
        this.initialState.hole = new int[]{i-1,j-1};
    }
    private int[][] createNewState(int[][] currentState, int emptyTileRow, int emptyTileCol, int newRow, int newCol) {
        // Create a new state by swapping the empty tile with a neighboring tile
        int[][] newState = new int[currentState.length][currentState[0].length];
        for (int i = 0; i < currentState.length; i++) {
            System.arraycopy(currentState[i], 0, newState[i], 0, currentState[i].length);
        }
        newState[emptyTileRow][emptyTileCol] = newState[newRow][newCol];
        newState[newRow][newCol] = 0; // Set the empty tile in the new position
        return newState;
    }

    public Stack<TreeNode<int[][]>> getSolutionPath() {
        return solutionPath;
    }
    public int getNumberOfVisitedNodes(){
        return numberOfVisitedNodes;
    }

    public int getNumberOfStoredNodes() {
        return numberOfStoredNodes;
    }

    public static void main(String[] args) {
        for (int f = 0; f < 6; f++) {
            for (int n = 3; n <= 3; n++) {
                int[][] goal = new int[n][n];
                int k = 0;
                for (int i = 0; i < n; i++)
                    for (int j = 0; j < n; j++) {
                        goal[i][j] = k;
                        k++;
                    }
                int averageDepth = 0, maxDepth = 0, minDepth = 0;
                int averageVisited = 0, maxVisited = 0, minVisited = 0;
                int averageStored = 0, maxStored = 0, minStored = 0;
                String statement;
                switch (f){
                    case 0 -> statement = "A* with manhattan distance heuristic";
                    case 1 -> statement = "A* with euclidean distance heuristic";
                    case 2 -> statement = "A* with misplacement heuristic";
                    case 3 -> statement = "BFS";
                    case 4 -> statement = "Iterative deepening";
                    case 5 -> statement = "DFS with revisit check";
                    case 6 -> statement = "DFS";
                    default ->
                        throw new IllegalStateException("Unexpected value: " + f);
                }
                System.out.println(statement+ " for n = " + n);
                long startTime = System.nanoTime();
                for (int i = 0; i < 10; i++) {
                    int[][] initialState = randomState(n);
                    mTilePuzzleSolver puzzle = new mTilePuzzleSolver(initialState, goal);
                    switch (f){
                        case 0 -> puzzle.AStar("manhattan");
                        case 1 -> puzzle.AStar("euclidean");
                        case 2 -> puzzle.AStar("misplaced");
                        case 3 -> puzzle.BFS();
                        case 4 -> puzzle.iterativeDeepening();
                        case 5 -> puzzle.DFSWithRevisitCheck();
                        case 6 -> puzzle.DFS();
                    }
                    Stack<TreeNode<int[][]>> solutionPath = puzzle.getSolutionPath();
                    int numberOfVisited = puzzle.getNumberOfVisitedNodes();
                    int depth = solutionPath.size - 1;
                    int numberOfStored = puzzle.getNumberOfStoredNodes();
                    System.out.println("Run #" + (i + 1));
                    averageDepth += depth;
                    averageVisited += numberOfVisited;
                    averageStored += numberOfStored;

                    if (numberOfVisited > maxVisited)
                        maxVisited = numberOfVisited;
                    if (depth > maxDepth)
                        maxDepth = depth;
                    if (numberOfStored > maxStored)
                        maxStored = numberOfStored;
                    if (i == 0) {
                        minVisited = solutionPath.size;
                        minDepth = solutionPath.size - 1;
                        minStored = puzzle.getNumberOfStoredNodes();
                    } else {
                        if (minVisited > numberOfVisited)
                            minVisited = numberOfVisited;
                        if (minDepth > depth)
                            minDepth = depth;
                        if (minStored > numberOfStored)
                            minStored = numberOfStored;
                    }
                    while (!solutionPath.isEmpty() && f < 5)
                        System.out.print((i + 1) + ": " + solutionPath.remove().origin + " ");
                }
                System.out.println("Descriptive statistics for "+statement);
                averageVisited /= 10;
                averageDepth /= 10;
                averageStored /= 10;
                System.out.println("Seconds Taken: " + ((System.nanoTime() - startTime) / 1000000000));
                System.out.println("Minimum depth: " + minDepth);
                System.out.println("Average depth: " + averageDepth);
                System.out.println("Maximum depth: " + maxDepth);
                System.out.println("Minimum number of visited states: " + minVisited);
                System.out.println("Average number of visited states: " + averageVisited);
                System.out.println("Maximum number of visited states: " + maxVisited);
                System.out.println("Minimum number of stored states: " + minStored);
                System.out.println("Average number of stored states: " + averageStored);
                System.out.println("Maximum number of stored states: " + maxStored);
            }
        }
    }
    public static int[][] randomState(int n) {
        int[][] state = new int[n][n];
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < n * n; i++)
            numbers.add(i);
        Collections.shuffle(numbers);
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                state[i][j] = numbers.remove(numbers.size() - 1);
        if (isSolvable(state, n))
            return randomState(n);
        return state;
    }
    private static int getInvCount(int[] arr, int n)
    {
        int inv_count = 0;
        for (int i = 0; i < n*n - 1; i++)
            for (int j = i + 1; j < n*n; j++)
                // Value 0 is used for empty space
                if (arr[i] > 0 && arr[j] > 0 && arr[i] > arr[j])
                    inv_count++;
        return inv_count;
    }
    private static boolean isSolvable(int[][] puzzle, int n)
    {
        int[] linearPuzzle;
        linearPuzzle = new int[n*n];
        int k = 0;

        // Converting 2-D puzzle to linear form
        for(int i=0; i < n ; i++)
            for(int j=0; j < n ; j++)
                linearPuzzle[k++] = puzzle[i][j];

        // Count inversions in given m tile puzzle
        int invCount = getInvCount(linearPuzzle, n);
        int holeRow = findHoleBottomRow(puzzle,n);
        return (n % 2 != 1 || invCount % 2 != 0) && (n % 2 != 0 || ((invCount % 2 + holeRow % 2) != 1));
    }
    private static int findHoleBottomRow(int[][] array,int n){
        for (int i = n - 1; i >= 0; i--)
            for (int j = n - 1; j >= 0; j--)
                if (array[i][j] == 0)
                    return n - i;
        return -1;
    }
}