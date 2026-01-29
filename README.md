# CleanBoot - Ein Vorschlag für saubere Backendprogrammierung mit Java/Springboot

Dieses Repository bietet einen Vorbereitungskurs zum Selbststudium für den Einstieg in den [CAS Secure Software Design & Development](https://www.zhaw.ch/de/engineering/weiterbildung/detail/kurs/cas-secure-software-design-development) an der ZHAW (https://www.zhaw.ch).

Aktuell besteht das Material aus einem ca. 90 seitigen Skript + Codebeispielen. Im Laufe der nächsten
Monate wird dieses Angebot durch ebenfalls offen zugängliche Lernvideos ergänzt.

## OER Lizenz
Sämtliches Material in diesem Repository ist als OER (Open Educational Resources) zur Verfügung gestellt
und kann unter der Creative Commons Share Alike International 4.0 Lizenz (CC-BY-SA 4.0) genutzt werden.
Für die Codebeispiele gilt die noch weniger restriktive MIT-Lizenz.

## Status
Das Material ist explizit noch im Aufbau- / Draft-Status.
Feedback / Issues und Pull-Requests sind explizit erwünscht.

## (EXPERIMENTAL) Devcontainers
Devcontainer support is currently experimental but not yet stable or necessarily safe to use.
Currently it runs on 3rd party images. Be careful if you experiment with this features.
Especially on the LaTeX part. The Java container should be fine, though.

## LaTeX Build
Für den Build wird `minted` verwendet. Ab `minted` v3 wird das externe
Executable `latexminted` benötigt und muss im `PATH` verfügbar sein.
Falls der Build fehlschlägt, prüfe zuerst die Verfügbarkeit von `latexminted`.

### Skript (script.pdf) erzeugen
Der Build ist über das `Makefile` automatisiert.

1. `make clean && make all`
2. Das Ergebnis liegt anschließend als `script.pdf` im Repository-Root.

Zwischendateien werden in `.out/` abgelegt. Aufräumen mit `make clean`.

### TeX-Rezepte (z.B. VS Code LaTeX Workshop)
Falls du lieber über ein Editor-Rezept baust, kannst du ein `latexmk`-Rezept
mit denselben Flags hinterlegen. Beispiel für `.vscode/settings.json`:

```json
{
  "latex-workshop.latex.tools": [
    {
      "name": "latexmk-cleanboot",
      "command": "latexmk",
      "args": [
        "-pdf",
        "-shell-escape",
        "-interaction=nonstopmode",
        "-halt-on-error",
        "-auxdir=.out",
        "-outdir=.out",
        "-out2dir=${workspaceFolder}",
        "script.tex"
      ]
    }
  ],
  "latex-workshop.latex.recipes": [
    {
      "name": "CleanBoot (latexmk)",
      "tools": [
        "latexmk-cleanboot"
      ]
    }
  ]
}
```

Danach in VS Code die Recipe **CleanBoot (latexmk)** wählen und bauen.

## Nutzung von generativer KI in diesem Repository
Die Verwendung von AI zur sprachlichen Überarbeitung, Korrektur und/oder zum Einholen von Feedback  zu Beiträgen  und Code zu diesem Skript ist in diesem Repository akzeptabel, eine originäre Erstellung der Inhalte durch eine AI jedoch nicht.

Die offene CC-Lizenz ermöglicht jedem mit dem Material zu tun, was immer er/sie möchte, selbstverständlich auch in Bezug auf AI. Pull-Requests mit offensichtlichem AI-Slop werde ich aber für dieses Repository nicht akzeptieren.

Sollte AI zur Erstellung von Code in diesem Repository verwendet werden ist es die Pflicht jedes
Autors die generierten Snippets/Code-Teile vollständig zu überprüfen und kritisch zu hinterfragen.
