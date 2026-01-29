# Base image: official Microsoft Dev Container base
FROM mcr.microsoft.com/devcontainers/base:bookworm

# Set non-interactive frontend and timezone
ENV DEBIAN_FRONTEND=noninteractive
ENV TZ="Europe/Zurich"

# Install essential system dependencies, minimal LaTeX for minted, Python, and Perl
RUN apt-get update && apt-get install -y --no-install-recommends \
    build-essential \
    curl wget git \
    python3 python3-pip \
    perl libyaml-tiny-perl \
    texlive-latex-base \
    texlive-latex-recommended \
    texlive-latex-extra \
    texlive-lang-german \
    texlive-fonts-recommended \
    texlive-fonts-extra \
    texlive-science \
    texlive-pictures \
    texlive-bibtex-extra \
    texlive-humanities \
    texlive-xetex \
    latexmk \
    chktex \
    texlive-luatex \
    texlive-pstricks \
    texlive-metapost \
    texlive-publishers \
    texlive-plain-generic \
    texlive-fonts-extra \
    biber \
    dvipng \
    epstool \
    inkscape \
    unzip \
    git \
    ghostscript \
    && pip3 install --break-system-packages Pygments latexminted \
    && ln -snf /usr/share/zoneinfo/$TZ /etc/localtime \
    && echo $TZ > /etc/timezone \
    && rm -rf /var/lib/apt/lists/*

ARG USERNAME=devcontainer
ARG USER_UID=1001
ARG USER_GID=1001

RUN groupadd --gid $USER_GID $USERNAME \
    && useradd -s /bin/bash --uid $USER_UID --gid $USER_GID -m $USERNAME \
    && chown -R $USERNAME:$USERNAME /home/$USERNAME
    
USER devcontainer
WORKDIR /home/devcontainer/workspace

# Compiler flags
ENV CXXFLAGS="-std=c++20" \
    CFLAGS="-std=c11" \
    npm_config_cxxflags="-std=c++20"
