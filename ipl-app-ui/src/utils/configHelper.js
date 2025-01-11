
const getConfig = (key, defaultValue) => {
  if (window && window[key]) {
    return window[key];
  }
  console.warn(
    `Configuration key "${key}" not found. Using default value: "${defaultValue}"`
  );
  return defaultValue;
};

export const APP_ROOT_URL = getConfig("APP_ROOT_URL", "http://localhost:8081/app");
export const APP_TOTAL_TEAM_SIZE = getConfig("APP_TOTAL_TEAM_SIZE", 20);
export const APP_DATA_START_YEAR = getConfig("APP_DATA_START_YEAR", 2008);
export const APP_DATA_END_YEAR = getConfig("APP_DATA_END_YEAR", 2024);
