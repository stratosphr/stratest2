#!/bin/bash

compile () {
    cd results;
    for folder in $(echo */);
    do
        cd $folder;
        for subfolder in $(echo */);
        do
            cd $subfolder;
            for dotFile in $(ls *.dot);
            do
                dot -Tpdf $dotFile > $(basename $dotFile .dot).pdf
            done
            for txtFile in $(ls *.mch *.stat *.txt *.ap *.as 2>/dev/null);
            do
                paps --font="Monospace 6" $txtFile > $txtFile.ps
                ps2pdf $txtFile.ps $txtFile.pdf&
            done
            cd ..;
        done
        cd ..;
    done
    cd ..
}

case $# in
    0) 
        compile;;
    1) 
        while true;
        do
            compile;
            sleep 1
        done;;
    *) 
        echo "USAGE: pdfMaker.sh [-l|--loop]";;
esac
