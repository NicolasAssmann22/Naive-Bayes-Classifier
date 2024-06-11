# NaiveBayesClassifier

Es wurde der Bernoulli Naive Bayes Klassifikator mit Laplace-Glättung mit Java implementiert.

## Benutzung des Programms

### Trainings und Testdateien

Um dem Programm Trainingsdaten hinzuzufügen, müssen diese entsprechend ihrer Klasse in das Verzeichnis 
``
trainingData/neg
``
oder
``
trainingData/pos
``
abgelegt werden. Die Trainingsdaten müssen ``.txt`` Dateien sein und jedes Dokument der Trainingsdaten muss in einer separaten
Datei sein, damit der Klassifikator die Anzahl der Dokumente korrekt zählen kann. Die zu klassifizierende 
Datei muss in das Verzeichnis ``trainingData/unclassified``. Diese muss ebenso eine ``.txt`` Datei sein.

### Kompilieren

Um das Programm zu kompilieren, gibt es zwei make Dateien. Eine ``make.sh`` für Linux und eine ``make.bat`` für Windows. Die
``make.sh`` muss möglicherweise Ausführungsrechte bekommen.

Aufruf:

```bash
.\make.bat
```

```bash
./make.sh
```

### Programm starten

Um das Programm zu starten muss eine zu klassifizierende Datei im Verzeichnis
``
trainingData/unclassified
``
sein. Für den Aufruf des Programms stehen wieder eine ``run.bat`` und eine ``run.sh`` zur Verfügung. Die
``run.sh`` muss möglicherweise Ausführungsrechte bekommen. Mit dem Aufruf muss der Dateiname der 
zu klassifizierenden Datei übergeben werden.

Aufruf:

Windows:

```bash
.\run.bat <Filename>
```

Bsp.:

```bash
.\run.bat document.txt
```

Linux:

```bash
./run.sh <Filename>
```
Bsp.:

```bash
./run.sh document.txt
```

Als Ergebnis werden die Wahrscheinlichkeiten für die Klasse ``pos`` und ``neg`` ausgegeben und die höhere Wahrscheinlichkeit 
bestimmt.

Beispielausgabe:
```
probability neg: 0.0576
probability pos: 0.0024000000000000007
probability for class neg is higher
```



