# -*- coding: utf-8 -*-
"""
Created on Wed Dec 12 10:15:11 2018

@author: kosmo30
"""

import numpy as np
import matplotlib.pyplot as plt
plt.style.use('ggplot')

N = 500
normal = np.random.normal(loc=0.0, scale=1.0, size=N)
longnormal = np.random.lognormal(mean=0.0, sigma=1.0, size=N)
index_value = np.random.random_integers(low=0, high=N-1, size=N)
normal_sample = normal[index_value]
longnormal_sample = longnormal[index_value]
box_plot_data = [normal, normal_sample, longnormal, longnormal_sample]

fig = plt.figure()
ax1 = fig.add_subplot(1,1,1)

box_lables = ['normal', 'normal_sample', 'longnormal', 'longnormal_sample']

ax1.boxplot(box_plot_data, notch=False, sym='.', vert=True, whis=1.5, \
            showmeans=True, labels=box_lables)

ax1.xaxis.set_ticks_position("bottom")
ax1.yaxis.set_ticks_position("left")
ax1.set_title('Box Plots: Resampling of Two Distributions')
ax1.set_xlabel('Distribution')
ax1.set_ylabel('Value')

plt.savefig('./output/05_box_plot.png', dpi=400, bbox_inches='tight')
plt.show()