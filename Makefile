LATEXMK ?= latexmk
TEX := script.tex
OUTDIR ?= .out
WORKSPACE ?= $(CURDIR)
OUTDIR_SUBDIRS ?= frontmatter chapter0 chapter1 chapter2 backmatter

all: check-minted
	mkdir -p $(OUTDIR) $(addprefix $(OUTDIR)/,$(OUTDIR_SUBDIRS))
	$(LATEXMK) -pdf -shell-escape -interaction=nonstopmode -halt-on-error \
		-cd -auxdir=$(OUTDIR) -outdir=$(OUTDIR) $(TEX)
	cp $(OUTDIR)/script.pdf ./script.pdf

check-minted:
	@command -v latexminted >/dev/null 2>&1 || \
		(echo "Error: minted v3+ executable 'latexminted' not found in PATH."; exit 1)

clean:
	rm -rf $(OUTDIR)
	rm -f *.bbl *.bcf *.blg *.run.xml quit.tex script.pdf
