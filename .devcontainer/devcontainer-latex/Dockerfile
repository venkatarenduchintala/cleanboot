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
    latexmk \
    chktex \
 && pip3 install --no-cache-dir Pygments \
 && ln -snf /usr/share/zoneinfo/$TZ /etc/localtime \
 && echo $TZ > /etc/timezone \
 && rm -rf /var/lib/apt/lists/*

# Use the existing devcontainer user
USER devcontainer
WORKDIR /home/devcontainer/workspace

# Compiler flags
ENV CXXFLAGS="-std=c++20" \
    CFLAGS="-std=c11" \
    npm_config_cxxflags="-std=c++20"
