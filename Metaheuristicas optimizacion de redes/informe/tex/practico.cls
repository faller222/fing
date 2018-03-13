
\NeedsTeXFormat{LaTeX2e}
\ProvidesClass{practico}[2013/02/20 Clase para prácticos]

\DeclareOption{consoluciones}{\AtEndOfClass{\includecomment{soluciones}}}
\DeclareOption{sinsoluciones}{\AtEndOfClass{\excludecomment{soluciones}}}
\ProcessOptions\relax

\LoadClass[a4paper,12pt]{article}

\RequirePackage{ifthen}
\RequirePackage{graphics}
\RequirePackage[spanish]{babel}
\RequirePackage[utf8]{inputenc}
\RequirePackage{amssymb}
\RequirePackage{amsmath}
\RequirePackage{amsthm}
\RequirePackage{comment}
\RequirePackage{enumerate}

%\RequirePackage{proof}
%\RequirePackage{macros}

\RequirePackage[margin=2cm]{geometry}

\newcommand{\PracticoNumero}[1]{\newcommand{\@NumeroPractico}{\textbf{Práctico #1}}}
\newcommand{\PracticoNombre}[1]{\newcommand{\@NombrePractico}{\textsl{#1}}}
\newcommand{\PracticoAnio}[1]{\newcommand{\@AnioPractico}{#1}}

\newcommand{\ps@practico}{
  \renewcommand{\@oddhead}{\slshape UTU \hfill Tecnólogo en Informática \hfill MDL2 \@AnioPractico}
  \renewcommand{\@oddfoot}{\hfill \slshape Página \thepage}}

\pagestyle{practico}

\renewcommand{\maketitle}{%
  \thispagestyle{practico}
  \begin{center}%
  \let \footnote \thanks
    {\LARGE \@NumeroPractico} \vskip 1em 
    {\LARGE \@NombrePractico} \vskip .5em
%%    {\scriptsize (\today)}
  \end{center}%
}

\newtheoremstyle{sty@Ejercicio}% name of the style to be used
  {}% measure of space to leave above the theorem. E.g.: 3pt
  {}% measure of space to leave below the theorem. E.g.: 3pt
  {}% name of font to use in the body of the theorem
  {}% measure of space to indent
  {\bfseries\large}% name of head font
  {\newline}% punctuation between head and body
  {.5em}% space after theorem head; " " = normal interword space
  {\thmname{#1} \thmnumber{#2} \ifthenelse{\equal{#3}{}}{}{(\thmnote{#3})}
  \vspace{1ex}} % Manually specify head
\theoremstyle{sty@Ejercicio}
\newtheorem{Ejercicio}{Ejercicio}

\AtBeginDocument{
  \maketitle
}


