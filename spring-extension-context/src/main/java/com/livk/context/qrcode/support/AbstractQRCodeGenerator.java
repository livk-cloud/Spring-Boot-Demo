/*
 * Copyright 2021-2024 spring-boot-extension the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.livk.context.qrcode.support;

import com.livk.context.qrcode.QRCodeEntity;
import com.livk.context.qrcode.QRCodeGenerator;

import java.awt.image.BufferedImage;

/**
 * The type Abstract qr code generator.
 *
 * @author livk
 */
public abstract class AbstractQRCodeGenerator implements QRCodeGenerator {

	@Override
	public final BufferedImage generateQRCode(QRCodeEntity<?> entity) {
		return generateQRCode(convert(entity.content()), entity.width(), entity.height(), entity.config(),
				entity.type());
	}

	/**
	 * Convert string.
	 * @param content the content
	 * @return the string
	 */
	protected abstract String convert(Object content);

}
