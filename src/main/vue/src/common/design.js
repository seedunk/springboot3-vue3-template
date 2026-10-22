
export function parseMarkdown(md, titleText) {
  // 按行分割，保留所有行（含空行）
  const lines = md.split('\n');
  
  // 1. 查找指定标题行（以 # 开头，且包含 titleText）
  let titleIndex = -1;
  for (let i = 0; i < lines.length; i++) {
    const line = lines[i];
    if (line.trim().startsWith('#')) {
      // 去掉前导 # 和空格，得到标题文本
      const cleaned = line.replace(/^#+\s*/, '').trim();
      if (cleaned.includes(titleText)) {
        titleIndex = i;
        break;
      }
    }
  }
  if (titleIndex === -1) return {}; // 未找到标题

  // 2. 从标题之后寻找第一个表格行
  let tableStart = -1;
  for (let i = titleIndex + 1; i < lines.length; i++) {
    const line = lines[i];
    if (line.includes('|')) {
      tableStart = i;
      break;
    }
    // 跳过空行（无 '|' 的行），继续寻找
  }
  if (tableStart === -1) return {}; // 未找到表格

  // 3. 收集连续的表格行（直到遇到非表格行）
  const tableLines = [];
  for (let i = tableStart; i < lines.length; i++) {
    const line = lines[i];
    if (line.includes('|')) {
      tableLines.push(line);
    } else {
      break; // 遇到非表格行停止
    }
  }

  // 至少需要表头 + 分隔行 + 一行数据
  if (tableLines.length < 3) return {};

  // 4. 解析表格（标准结构：表头行、分隔行、数据行）
  // 表头行（tableLines[0]）忽略，分隔行（tableLines[1]）忽略
  const dataLines = tableLines.slice(2); // 从第三行开始是数据

  const result = {};
  for (const line of dataLines) {
    const cells = line.split('|').map(c => c.trim()).filter(c => c !== '');
    // 确保至少有三列：Key | 默认值 | 说明
    if (cells.length >= 3) {
      const key = cells[1];   // 第二列为 Key
      const value = cells[2]; // 第三列为默认值
      result[key] = value;
    }
  }
  return result;
}
 

import darkThemeRaw from '@design/Dark.md?raw';
import baseThemeRaw from '@design/Theme.md?raw'; 

const themeMap = {
    dark: darkThemeRaw,
    theme: baseThemeRaw 
};
 
export function getThemeToken(themeName) {
 
      const themeRaw = themeMap[themeName] || baseThemeRaw;   
      return parseMarkdown(themeRaw,"配色方案");
     
}