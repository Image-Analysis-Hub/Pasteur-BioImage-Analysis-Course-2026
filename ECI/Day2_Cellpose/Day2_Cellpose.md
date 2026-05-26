# <img src="assets/cp_logo.png" alt="Cellpose logo" width="50" /> Cellpose for machine learning in image analysis
**Instructor**: Julie Mabon, Post-doc, Unité d'Analyse d'Images Biologiques, Institut Pasteur

> [!NOTE]
> In this course we use Cellpose 3[^cp3] instead of the latest Cellpose-SAM[^cpsam] as it is faster to run on any laptop and easier to re-train.

## 🧑‍💻 Getting ready

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


## 🪄 Part 1: Cellpose-Appose on Fiji

First use case: use Cellpose 3[^cp3] to segment nuclei and count spot area (intensity?) in each nucleus, then export a CSV using the [Cellpose - Appose Fiji plugin](https://github.com/Image-Analysis-Hub/cellpose-appose).


1. Load the image
2. Launch the Cellpose plugin:
    - `Plugins > Segmentation > Cellpose-Appose > Cellpose...`
3. Set up parameters as suggested. You can hover your mouse over parameters to see what they do.

> [!IMPORTANT]
> - Choose appropriate `Cellpose Model`: here we use `nuclei` since we are segmenting nuclei; for cytoplasm use `cyto3`.
> - Set Cytoplasmic/Nuclei channel according to your image: here we don't have a cytoplasmic channel (→ None), and the nuclei staining is in channel 4
> - Make sure to check `return ROIs` to get ROIs in Fiji for our measurements

![Cellpose-Appose plugin parameters](assets/image-cp_appose.png)


4. Click OK (it may take a while the first time to set up the Python environment)

> [!NOTE]
> The Python environment will be automatically installed in your home `.local/shared/appose` directory and activated from the plugin when needed.

5. Check the results. Check `Show All` in the `ROI Manager` to see all ROIs, `Labels` to show instance labels

![ROI Manager with labels displayed](assets/image-ROI_manager.png)

6. Now we are going to set up the measurements to extract for each nucleus ROI. Customize measurements in `Analyse > Set Measurements` (see documentation at https://imagej.net/ij/docs/menus/analyze.html#set) 

![Set Measurements dialog](assets/image-set_measurments.png)

7. Select the spots channel (ch 3/4) since this is what we want to measure.
8. In `ROI Manager`, select all with `Ctrl+A` (`Cmd+A` on Mac). Then go to `ROI Manager > More > Multi Measure` and set as follows: 

![Multi Measure settings](assets/image-multi_measure.png)

9. You should get something like this: one row per nucleus ROI, with measurements from channel 3. Save with `Results > File > Save As...`

![Results table with measurements](assets/image_results.png)


## 🎛️ Part 2: When it does not work out of the box

### Tuning diameter

Let's open [IBIDI.tif](./images/IBIDI.tif) and try to extract the nuclei

- Try nuclei detection with default `Diameter` = 30
  - (model=nuclei, cyto channel=None, nuclei channel=2)
- Update the diameter to fit the cells in the image and check results
- Try also with `File> Open Samples > Hela Cells`

<details><summary>Results on IBIDI.tif (diameter = 30)</summary>
<img src="assets/image-IBIDI_d30.png" alt="Nuclei detection with diameter 30" />
</details>

<details><summary>Results on IBIDI.tif (diameter = 200)</summary>
<img src="assets/image-IBIDI_d200.png" alt="Nuclei detection with diameter 200" />
</details>

> [!TIP]
> You can use the line tool to measure your cell diameter in the image:
> ![Line tool measurement](assets/image-length.png)
> Length is in µm, use `Ctrl+I` to display image info and get the pixel/µm
> ![Image info dialog](assets/image-image_info.png)
> Then you can get: cell diameter (px) = resolution (px/µm) × length (µm)


### Tuning contrast
Our [IBIDI.tif](./images/IBIDI.tif) also has information about the cytoplasm (DAPI + cellmask in the same channel), but the nuclei are very bright and the cytoplasm is very faint. Let's play around with the image contrast to get the cytoplasm!

**🏗️ WIP**

- Retinex 
- Segment again


## 🏋️ Part 3: Fine-tuning using Cellpose-GUI


1. In the `cellposegui` environment (make sure you ran `mamba activate cellposegui`), run `cellpose`. You should get something like this:
![Cellpose-GUI interface](assets/image-cellpose_gui.png)


**🏗️ WIP**

- download some data
- try segmentation
- correct segmentation
- train a bit
- check segmentation

# 📝 Take-home message

- For any biological image instance segmentation task, I suggest proceeding as follows until you get sufficiently good results:
```mermaid
flowchart TD
    I([biological image instance segmentation task]) --> O[🛠️ Try classic methods eg: thresholding, connected components labeling]
    O -->|good results 👍| S([🥳Success])
    O -->|bad results 🤷| A[🪄 Try pretrained Cellpose 3 on Fiji or other]
    A -->|good results 👍| S
    A -->|bad results 😕| B[🎛️ Play around with model parameters and image contrast]
    B -->|good results 👍| S
    B -->|bad results 😟| C[🪄 Try with Cellpose-SAM]
    C -->|good results 👍| S
    C -->|bad results 😣| D[🏋️ Retrain Cellpose 3 on small annotated images/patches]
    D -->|good results 👍| S
    D -->|bad results 😰| F([🧰 Try another tool 
     🙋 Ask for help])
```

# 💽 Data
- [AB3-HG-AQUCISITION-4CHANNELS-SHRNActrl-filtered_crop.tif](./images/AB3-HG-AQUCISITION-4CHANNELS-SHRNActrl-filtered_crop.tif): crop + maxproj from https://zenodo.org/records/17048217
  > _3D confocal images of dorsal views of dissected telencephala from 4 months post-fertilization (mpf) zebrafish. Dataset of images to complement the paper: FishFeats: streamlined quantification of multimodal labeling at the single-cell level in 3D tissues. Bit depth for all images acquired was 16 bit, a tile scan of multiple Z stacks all with a voxel size of 0.207 µm × 0.207 µm × 0.5 µm. Immunohistochemistry (IHC) for Zo1 (Zonula occludens 1) outlines the apical cell contours, and IHC of Sox2 is used for the identification of neural stem cells and progenitor cells corresponding mostly to the first layer of cells. Expression of pcna, her4 and hey1 (in green, orange and red) were detected using fluorescent in situ hybridization (FISH)._
- [IBIDI.tif](./images/IBIDI.tif): From D. BROKATZKY, Pasteur - [Dynamics of Host-Pathogen Interactions](https://research.pasteur.fr/fr/team/dynamics-of-host-pathogen-interactions/)
  > chan0: DAPI and cell mask; chan1: Salmonella bacteria; chan2: NFKBIA RNA

[^cp3]: Stringer, C. & Pachitariu, M. (2025). Cellpose3: one-click image restoration for improved segmentation. Nature Methods. https://www.nature.com/articles/s41592-025-02595-5
[^cpsam]: Pachitariu, M., Rariden, M., & Stringer, C. (2025). Cellpose-SAM: superhuman generalization for cellular segmentation. bioRxiv. https://www.biorxiv.org/content/10.1101/2025.04.28.651001v1

