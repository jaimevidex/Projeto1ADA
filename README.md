# Projeto1ADA — GeometryLand

An algorithms exercise on circular sequences: a ring of integers joined by operators, where you must remove one operator and find the arrangement that maximises the value of the expression.

Written in Java for the *Análise e Desenho de Algoritmos* course at Instituto Superior Técnico.

## The problem

You are given `n` integers arranged in a circle, with `n` operators (`+` and `*`) between them. Removing one operator turns the circle into a line. For every possible operator to remove, the program evaluates the line left to right and records the maximum attainable value.

Two results are printed:

1. **The global maximum** over every starting operator.
2. **Every operator index** (1-based) whose removal achieves that maximum.

Long arithmetic is used throughout (`long`), because repeated multiplication overflows `int` quickly.

## Input

```
4
+ 3 * 2 + 5 * 1
```

- Line 1: `n`, the number of integers.
- Line 2: `2n` alternating tokens — operator, integer, operator, integer, …

## Output

```
60
2 4
```

- Line 1: the maximum value.
- Line 2: the 1-based indices of the operators whose removal reaches it.

## Run

```bash
javac src/*.java -d out
java -cp out Main
```

The program reads from standard input, so pipe a file in or type the values directly.

## Structure

| File | Responsibility |
|---|---|
| `src/Main.java` | Parses input, calls the solver, prints results |
| `src/GeometryLandSystem.java` | Rotation evaluation and maximum search |
| `geometryland.pdf` | Original assignment statement |
