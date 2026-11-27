import matplotlib.pyplot as plt
import os
import re
import numpy as np
import time
import numpy as np

FILE_PATH = os.path.join( 'Data', 'output', 'output1.out')

plt.ion() 
fig, ax = plt.subplots(figsize=(8, 8))

def setup_axes():
    ax.set_xlim(-15, 15)
    ax.set_ylim(-6, 6)
    ax.set_aspect('equal')
    ax.spines['left'].set_position('center')
    ax.spines['bottom'].set_position('center')
    ax.spines['right'].set_color('none')
    ax.spines['top'].set_color('none')
    ax.grid(True, linestyle=':', alpha=0.3)
    
    # Reference y = |x|
    ref_x = np.linspace(-100, 100, 400)
    ax.plot(ref_x, np.abs(ref_x), color='black', linestyle='--', alpha=0.2)

setup_axes()

x_history, y_history = [], []
line, = ax.plot([], [], 'b-', alpha=0.4, lw=1.5)
dot, = ax.plot([], [], 'r.', markersize=1, zorder=10)
ax.plot()
v_arrow = ax.quiver(0, 0, 0, 0, color='green', 
                    angles='xy', scale_units='xy', scale=1, 
                    width=0.007, headwidth=4, headlength=5, zorder=11)

def get_numbers(line_str):
    nums = re.findall(r"[-+]?\d*\.\d+|\d+", line_str)
    if len(nums) >= 5:
        return {
            'vx': float(nums[1]),
            'vy': float(nums[2]),
            'px': float(nums[3]),
            'py': float(nums[4])
        }
    return None

# print(f"Monitoring: {os.path.abspath(FILE_PATH)}")

# def run_plotter():
#     while not os.path.exists(FILE_PATH):
#         time.sleep(1)
        
#     with open(FILE_PATH, 'r') as f:
#         while True:
#             line_str = f.readline()
            
#             if not line_str:
#                 plt.pause(0.01)
#                 continue
                
#             data = get_numbers(line_str)
#             if data:
#                 x_history.append(data['px'])
#                 y_history.append(data['py'])
                
#                 # Update Visuals
#                 line.set_data(x_history[-100:], y_history[-100:])
#                 dot.set_data([data['px']], [data['py']])
                
#                 # Update Vector: length now scales with speed, thickness stays same
#                 v_arrow.set_offsets([data['px'], data['py']])
#                 v_arrow.set_UVC(data['vx'], data['vy'])
                
#                 plt.draw()
#                 plt.pause(0.001)

# try:
#     run_plotter()
# except KeyboardInterrupt:
#     print("Stopped.")
# try:
#     energy = []
#     while not os.path.exists(FILE_PATH):
#         time.sleep(1)
        
#     with open(FILE_PATH, 'r') as f:
#         while True:
#             line_str = f.readline()
            
#             if not line_str:
#                 plt.pause(0.01)
#                 continue
                
#             data = get_numbers(line_str)
#             energy.append(data['vx']**2 + data['vy']**2 + 9.81*2*data['py'])
# except KeyboardInterrupt:
#     print(max(energy)-min(energy))
#     print(sum(energy)/len(energy))
#     print(energy[-1])

def run_plotter():
    while not os.path.exists(FILE_PATH):
        time.sleep(1)
        
    with open(FILE_PATH, 'r') as f:
        while True:
            line_str = f.readline()
            
            if not line_str:
                plt.pause(0.01)
                continue
                
            data = get_numbers(line_str)
            if data:
                if(np.abs(data['px']) > data['py'] or data['py'] < 0):
                    continue
                x_history.append(data['px'])
                y_history.append(data['py'])
                
                # Update Visuals
                # line.set_data(x_history[-100:], y_history[-100:])
                dot, = ax.plot([], [], 'ro', markersize=1, zorder=10)
                position_vector = np.array([data['px'],data['py']])
                velocity_vector= np.array([data['vx'],data['vy']])
                radius = np.sqrt(sum(position_vector**2))
                radial_velocity = np.dot(velocity_vector,position_vector)/radius
                # if (data['px'] < 0):
                #     continue 

                dot.set_data([radius], [radial_velocity])
                if (data['px'] < 0):
                    dot.set_color("green")
                else:
                    dot.set_color("red")
                # radial_velocity = np.sqrt(sum(((np.dot(velocity_vector,position_vector)/radius**2) * position_vector)**2))
                # if (np.dot(velocity_vector, position_vector) > 0):
                #     dot.set_data([radius], [radial_velocity])
                # else:
                #     dot.set_data([radius], [-radial_velocity])
                # Update Vector: length now scales with speed, thickness stays same
                # v_arrow.set_offsets([data['px'], data['py']])
                # v_arrow.set_UVC(data['vx'], data['vy'])
                # plt.pause(0.001)


for t in range (1,20):
    FILE_PATH = os.path.join( 'Data', 'output', 'output' +str(t) + '.out')
    print("hit")
    try:
        run_plotter()
        plt.draw()
    except KeyboardInterrupt:
        print("Stopped.")
