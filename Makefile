all:
	pdflatex script.tex
	biber script
	pdflatex script.tex
	pdflatex script.tex
	make clean

clean:
	rm -rf _minted/ *.log *.aux *.out *.toc
	rm -rf frontmatter/*.aux
	rm -rf chapter0/*.aux
	rm -rf chapter1/*.aux
	rm -rf chapter2/*.aux
	rm -rf backmatter/*.aux
	rm -f *.bbl *.bcf *.blg *.run.xml quit.tex

