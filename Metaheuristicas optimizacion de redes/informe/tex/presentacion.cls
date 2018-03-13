
\NeedsTeXFormat{LaTeX2e}
\ProvidesClass{presentacion}[2013/03/06 Clase para presentaciones]

% \LoadClass[handout]{beamer}
\LoadClass{beamer}

\usetheme{Madrid}
\usecolortheme{crane}
\setbeamertemplate{navigation symbols}{}

\RequirePackage{ifthen}
\RequirePackage{graphics}
\RequirePackage[spanish]{babel}
\RequirePackage[utf8]{inputenc}
\RequirePackage{amssymb}
\RequirePackage{amsmath}
\RequirePackage{amsthm}
\RequirePackage{comment}
\RequirePackage{enumerate}

\RequirePackage{tikz}

\RequirePackage{proof}
%\RequirePackage{macros}

\newcommand{\PracticoNumero}[1]{\newcommand{\@NumeroPractico}{Práctico #1}}
\newcommand{\PracticoNombre}[1]{\newcommand{\@NombrePractico}{#1}}
\newcommand{\PracticoAutor}[1]{\newcommand{\@AutorPractico}{#1}}
\newcommand{\PracticoFecha}[1]{\newcommand{\@FechaPractico}{#1}}
\newcommand{\PracticoFechaCorta}[1]{\newcommand{\@FechaCortaPractico}{#1}}
\newcommand{\PracticoInst}[1]{\newcommand{\@InstPractico}{#1}}
\newcommand{\PracticoInstCorto}[1]{\newcommand{\@InstCortoPractico}{#1}}


\AtBeginDocument{
  \title{\@NumeroPractico}
  \subtitle{\@NombrePractico}
  \author{\@AutorPractico}
  \date[\@FechaCortaPractico]{\@FechaPractico}
  \institute[\@InstCortoPractico]{\@InstPractico}

  \begin{frame}
    \titlepage
  \end{frame}
}


