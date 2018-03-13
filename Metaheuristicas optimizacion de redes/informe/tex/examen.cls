
\NeedsTeXFormat{LaTeX2e}
\ProvidesClass{examen}[2009/07/23 Clase para exámenes]
%\ProvidesClass{exa33}[2009/06/25 Clase para exámenes]
%\ProvidesClass{exa3}[2008/04/29 Clase para exámenes]
%\ProvidesClass{exa3}[2007/12/13 Clase para exámenes]
%\ProvidesClass{exa3}[2006/12/15 Clase para exámenes]

\RequirePackage{ifthen}
\RequirePackage{environ}

\DeclareOption{examen}{
  \newcommand{\pkoInstancia}{Examen}
  \newcommand{\pkoinstancia}{examen}
  \newcommand{\pko@puntaje}{100}}
\DeclareOption{primerparcial}{
  \newcommand{\pkoInstancia}{Primer parcial}
  \newcommand{\pkoinstancia}{parcial}
  \newcommand{\pko@puntaje}{40}}
\DeclareOption{segundoparcial}{
  \newcommand{\pkoInstancia}{Segundo parcial}
  \newcommand{\pkoinstancia}{parcial}
  \newcommand{\pko@puntaje}{60}}
%\DeclareOption{consoluciones}{\AtEndOfClass{\includecomment{soluciones}}}
%\DeclareOption{consoluciones}{\AtEndOfClass{\newenvironment{soluciones}{}{}}}
\DeclareOption{consoluciones}{\AtEndOfClass{\NewEnviron{soluciones}{\BODY}}}
%\DeclareOption{sinsoluciones}{\AtEndOfClass{\excludecomment{soluciones}}}
%\DeclareOption{sinsoluciones}{\AtEndOfClass{\newenvironment{soluciones}{begin \collect@body\myBody }{ end}}
\DeclareOption{sinsoluciones}{\AtEndOfClass{\NewEnviron{soluciones}{}}}


\DeclareOption{fullwidth}{\newboolean{fullwidth}\setboolean{fullwidth}{true}}
\DeclareOption{nofullwidth}{\newboolean{fullwidth}}
\ProcessOptions\relax

\LoadClass[a4paper,12pt]{article}

\RequirePackage{graphicx}

\RequirePackage[spanish]{babel}
\RequirePackage[utf8]{inputenc}
\RequirePackage{amssymb}
\RequirePackage{amsmath}
\RequirePackage{comment}
\RequirePackage{enumerate}
\RequirePackage{mathtools}
\RequirePackage{environ}

%\RequirePackage{/inco/group01/logica/TeX/proof}
\RequirePackage{proof}
%RequirePackage{/inco/group01/logica/TeX/macros}
%\RequirePackage{macros}
%\RequirePackage{/inco/group01/logica/TeX/macros/macVanDalen}

\RequirePackage[margin=2cm]{geometry}

\renewcommand{\maketitle}{%
  \thispagestyle{empty}
  \begin{center}%
  \let \footnote \thanks
    {\LARGE \textbf{\@title} \par}%
    \vskip 1em%
    {\textbf{\@date}}%
  \end{center}%
}

\newcommand{\ps@examen}{
  \renewcommand{\@oddhead}{\slshape UTU \hfil \pkoInstancia}
  \renewcommand{\@oddfoot}{\slshape \rightmark\hfil\thepage}}

\pagestyle{examen}

\newenvironment{Indicaciones}{\begin{small}\textbf{Indicaciones generales}
\begin{itemize}\setlength{\itemsep}{0em}}{\end{itemize}\end{small}}

\newcommand{\pkopuntaje}{\item Puntaje: \textbf{\pko@puntaje} puntos.}
\newcommand{\pkopuntosej}[1]{(#1 puntos)}

%% \newcommand{\preguntaAster}[1]{\paragraph{Pregunta *.} {#1} \hfill$\clubsuit$\\}

\AtBeginDocument{
  \title {\pkoInstancia\ de MDL2}
  \maketitle
}
