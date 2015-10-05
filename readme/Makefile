TEX=$(wildcard *.tex)
PDF=readme.pdf
BUILD=pdflatex
SRC=readme.tex

all: ${PDF}

${PDF}: ${TEX}
	${BUILD} ${SRC}
	${BUILD} ${SRC}

clean:
	rm -rf *.pdf *.aux *.log *.out

.PHONY: clean
