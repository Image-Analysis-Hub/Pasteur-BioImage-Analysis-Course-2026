### For part 1 & 2
> [!WARNING] Requirements
> - Check you have the latest **Fiji/ImageJ** version with Java > 21
![Fiji version check](assets/image_fiji.png)
>  - if not get it there: https://imagej.net/software/fiji/downloads

> [!IMPORTANT] Cellpose - Appose Fiji plugin installation
> 1. with `Help > Update... > Manage Update Sites > Add Unlisted Site`
> 2. name it `Appose-Playground` and write its address `https://sites.imagej.net/Appose-Playground`
> 3. `Apply and close`, leave all `.jar` checked
> 4. `Apply Changes`
> 5. restart Fiji 
> More info: https://github.com/Image-Analysis-Hub/cellpose-appose

### For part 3

> [!WARNING] Requirements
> - Make sure you have **Mamba** or **Conda** (type `mamba info` or `conda info` in the terminal, it should give you a bunch of version information). Else install it from here https://github.com/conda-forge/miniforge#install
> - Set up the Python environment: 


> [!IMPORTANT] Installation of Cellpose in Python
> **Option 1** In the terminal:
> 1. Create the environment: `mamba create -n cellposegui python=3.12 -y`
> 2. Activate the environment: `mamba activate cellposegui`
> 3. Install the proper version of Cellpose: `python -m pip install 'cellpose[gui]'==3.1.1.3`
> 
> **Option 2** Run `mamba create -f cellpose_env.yaml`
> 
> You only have to do this **once**. Once the environment is set up, just run `mamba activate cellposegui` to activate.

> [!TIP]
> Here I use `mamba` but you can just replace it with `conda` if you only have that