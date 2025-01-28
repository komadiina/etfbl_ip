import Papa from 'papaparse';
import api from '../utils/api';

export const handleCSVUpload = async (file) => {
  return new Promise((resolve, reject) => {
    Papa.parse(file, {
      header: true,
      complete: async (results) => {
        const devices = results.data;
        const uploadResults = [];

        for (const device of devices) {
          try {
            let endpoint;
            let payload = {
              uuid: device.uuid,
              deviceType: device.deviceType,
              acquisitionDate: device.acquisitionDate,
              purchasePrice: parseFloat(device.purchasePrice),
              manufacturerID: parseInt(device.manufacturerID),
              model: device.model,
              description: device.description,
              status: device.status || 'Available'
            };

            switch (device.deviceType.toLowerCase()) {
              case 'vehicle':
                endpoint = '/vehicles';
                break;
              case 'electricbicycle':
                endpoint = '/electric-bicycles';
                if (device.autonomy) payload.autonomy = parseInt(device.autonomy);
                break;
              case 'electricscooter':
                endpoint = '/electric-scooters';
                if (device.maxSpeed) payload.maxSpeed = parseInt(device.maxSpeed);
                break;
              default:
                throw new Error(`Unknown device type: ${device.deviceType}`);
            }

            const response = await api.instance.post(endpoint, payload);
            uploadResults.push({
              success: true,
              deviceType: device.deviceType,
              uuid: response.data.uuid,
              message: `Successfully added ${device.deviceType}: ${response.data.uuid}`
            });
          } catch (error) {
            uploadResults.push({
              success: false,
              deviceType: device.deviceType,
              uuid: device.uuid,
              message: `Error adding device: ${error.message}`
            });
          }
        }

        resolve({
          totalProcessed: devices.length,
          successful: uploadResults.filter(r => r.success).length,
          failed: uploadResults.filter(r => !r.success).length,
          results: uploadResults
        });
      },
      error: (error) => {
        reject(error);
      }
    });
  });
};

export const utils = {
  tdMap: {
    'Vehicle': "Car",
    'ElectricScooter': 'e-Scooter',
    'ElectricBicycle': 'e-Bicycle'
  }
}

export default utils;
